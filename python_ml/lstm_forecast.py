import pandas as pd
import numpy as np
import torch
from torch import nn
from sklearn.preprocessing import MinMaxScaler
from torch.utils.data import Dataset, DataLoader
from copy import deepcopy as dc

device = "cuda" if torch.cuda.is_available() else "cpu"


def load_data():
    return pd.read_csv("owid-covid-data.csv")


def preprocess_data(df, country, disease):
    keep_columns = ["iso_code", "location", "date", "new_cases", "reproduction_rate", "new_deaths", "positive_rate",
                    "population_density", "median_age", "hospital_beds_per_thousand", "stringency_index"]

    new_df = df[keep_columns].copy()

    new_df["date"] = pd.to_datetime(new_df['date'], format="%Y-%m-%d")

    for col in keep_columns:
        print(f"{col}: {new_df[col].isna().sum()}")

    new_df.rename(columns={"location": "country"}, inplace=True)

    country_df = new_df[new_df["country"] == country].copy()

    nan_cols = []
    for col in country_df.columns:
        nan = country_df[col].isna().sum()
        print(f"{col}: {nan}")
        if nan > 0:
            nan_cols.append(col)

    for col in nan_cols:
        country_df[col] = country_df[col].fillna(country_df[col].median())

    data = country_df.drop(["iso_code", "country"], axis=1).copy()

    data = data.set_index("date")

    features = len(data.columns)

    scaler = MinMaxScaler()
    scaled_df = scaler.fit_transform(data)

    lookback = 21

    X, y = [], []
    for i in range(lookback, len(scaled_df)):
        X.append(scaled_df[i - lookback: i])
        y.append(scaled_df[i, 0])

    X, y = np.array(X), np.array(y).reshape(-1, 1)

    split_index = int(len(X) * 0.95)

    X_train = X[:split_index]
    X_test = X[split_index:]
    y_train = y[:split_index]
    y_test = y[split_index:]

    X_train = torch.tensor(X_train).float()
    X_test = torch.tensor(X_test).float()
    y_train = torch.tensor(y_train).float()
    y_test = torch.tensor(y_test).float()

    return X_train, X_test, y_train, y_test, features, scaler, lookback, split_index, data


def train_model(X_train, X_test, y_train, y_test, features):
    class TimeSeriesDataset(Dataset):
        def __init__(self, X, y):
            self.X = X
            self.y = y

        def __len__(self):
            return len(self.X)

        def __getitem__(self, i):
            return self.X[i], self.y[i]

    train_dataset = TimeSeriesDataset(X_train, y_train)
    test_dataset = TimeSeriesDataset(X_test, y_test)

    batch_size = 8
    train_loader = DataLoader(train_dataset, batch_size=batch_size, shuffle=False)
    test_loader = DataLoader(test_dataset, batch_size=batch_size, shuffle=False)

    for _, batch in enumerate(train_loader):
        x_batch, y_batch = batch[0].to(device), batch[1].to(device)
        print(x_batch.shape, y_batch.shape)
        break

    class LSTM(nn.Module):
        def __init__(self, input_size, hidden_size, num_stacked_layers):
            super().__init__()
            self.hidden_size = hidden_size
            self.num_stacked_layers = num_stacked_layers

            self.lstm = nn.LSTM(input_size, hidden_size, num_stacked_layers, batch_first=True, dropout=0.2)
            self.fc = nn.Linear(hidden_size, 1)

        def forward(self, x):
            batch_size = x.size(0)
            h0 = torch.zeros(self.num_stacked_layers, batch_size, self.hidden_size).to(device)
            c0 = torch.zeros(self.num_stacked_layers, batch_size, self.hidden_size).to(device)
            out, _ = self.lstm(x, (h0, c0))
            out = self.fc(out[:, -1, :])
            return out

    model = LSTM(features, 64, 2)
    model.to(device)

    def train_one_epoch():
        model.train(True)
        print(f"Epoch: {epoch + 1}")
        running_loss = 0.0

        for batch_index, batch in enumerate(train_loader):
            x_batch, y_batch = batch[0].to(device), batch[1].to(device)

            output = model(x_batch)
            loss = loss_function(output, y_batch)
            running_loss += loss.item()
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

            if batch_index % 100 == 99:
                avg_loss_across_batches = running_loss / 100
                print(f"Batch ({batch_index + 1}), Loss: {avg_loss_across_batches:.3f}")

                running_loss = 0.0
        print()

    def validate_one_epoch():
        model.eval()
        running_loss = 0.0

        for batch_index, batch in enumerate(test_loader):
            x_batch, y_batch = batch[0].to(device), batch[1].to(device)

            with torch.inference_mode():
                output = model(x_batch)
                loss = loss_function(output, y_batch)
                running_loss += loss.item()

        avg_loss_across_batches = running_loss / len(test_loader)
        print(f"Val Loss: {avg_loss_across_batches:.3f}")
        print("*******************************************************")
        print()

    torch.manual_seed(42)

    learning_rate = 0.001
    num_epochs = 20
    loss_function = nn.HuberLoss()
    optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)

    for epoch in range(num_epochs):
        train_one_epoch()
        validate_one_epoch()

    return model


def predict(model, X_test, features, scaler, lookback, split_index, data):
    model.eval()
    with torch.inference_mode():
        test_predictions = model(X_test.to(device)).detach().cpu().numpy().flatten()

    dummies = np.zeros((len(test_predictions), features))
    dummies[:, 0] = test_predictions
    dummies = scaler.inverse_transform(dummies)

    test_predictions = dc(dummies[:, 0])

    dates = data.index
    target_dates = dates[lookback:]
    test_dates = target_dates[split_index:]

    return test_dates, test_predictions
