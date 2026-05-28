from fastapi import FastAPI
from pydantic import BaseModel
from lstm_forecast import load_data, preprocess_data, train_model, predict

app = FastAPI()

items = []


class ForecastData(BaseModel):
    country_name: str
    disease_name: str


@app.put("/")
def update_data(forecast_data: ForecastData):
    global items

    print("Received from Java:")
    print(forecast_data)

    items = []

    df = load_data()

    X_train, X_test, y_train, y_test, features, scaler, lookback, split_index, data = (
        preprocess_data(df, forecast_data.country_name, forecast_data.disease_name))

    model = train_model(X_train, X_test, y_train, y_test, features)

    test_dates, test_predictions = predict(model, X_test, features, scaler, lookback, split_index, data)

    items.append((test_dates, test_predictions))

    return {"message": "Data received"}


@app.get("/")
def get_data():
    result = []

    for a, b in items:
        result.append({
            "test_dates": a.tolist(),
            "test_predictions": b.tolist()
        })

    return result
