# 🏥 HealthSense: Disease Outbreak Analysis System

## 📌 Overview

**HealthSense** is a console-based disease outbreak analytics and forecasting system developed to help epidemiologists and healthcare organizations monitor, manage, analyze, and predict disease outbreaks across multiple hospitals and regions.

The system combines:

- **Java CLI Application** for outbreak management and analytics
- **Python FastAPI Server** for Machine Learning forecasting
- **LSTM Neural Network Model** for outbreak prediction
- **CSV-based historical datasets** for training and analysis

HealthSense demonstrates the integration of:
- Data Structures & Algorithms
- Machine Learning
- API Communication
- Time-Series Forecasting
- Multi-language System Architecture

---

# 🚀 Features

## ✅ Core Analytics Features

### 📍 Region-Based Weekly Reports
- Add outbreak reports from multiple hospitals
- Store:
    - Region
    - Hospital
    - Disease
    - Week
    - Patient Count

---

### 🔍 Smart Search System
Search outbreak data using:
- Disease name
- Hospital name
- Patient count

---

### 📊 Sorting & Trend Analysis
- Sort diseases by total patient count
- Sort reports by week
- Analyze outbreak trends

---

### 🌳 Binary Search Tree (BST) Analysis
- Analyze outbreak severity efficiently
- Classify diseases based on patient count

---

### 🔁 Undo Latest Operation
- Stack-based undo functionality
- Restore previous system state

---

### 📋 Queue Management
- Queue-based outbreak report processing
- FIFO outbreak handling system

---

### 🖥️ Interactive CLI Interface
- User-friendly console menu system
- Easy outbreak report management

---

# 🧠 Disease Forecasting System (LSTM)

## 📌 Overview

HealthSense includes an advanced outbreak forecasting module powered by:

- **Python FastAPI**
- **PyTorch**
- **LSTM (Long Short-Term Memory) Neural Network**

The forecasting system predicts future disease outbreaks using historical time-series patient data.

---

# 🔮 Forecasting Workflow

1. User enters:
    - Country
    - Disease

2. Java application sends an HTTPS request to FastAPI server

3. FastAPI server:
    - Reads CSV dataset
    - Filters data by country and disease
    - Preprocesses time-series data
    - Trains LSTM model
    - Predicts future outbreak counts

4. Prediction result is returned to Java CLI

5. Java displays outbreak forecast to user

---

# 🏗️ System Architecture

```text
+----------------------+
|   Java CLI System    |
|----------------------|
| Search / Sort / BST  |
| Queue / Stack Logic  |
| Forecast Requests    |
+----------+-----------+
           |
           | HTTPS Request
           v
+----------------------+
|   FastAPI Server     |
|----------------------|
| CSV Data Processing  |
| LSTM Model Training  |
| Prediction API       |
+----------+-----------+
           |
           v
+----------------------+
| Historical CSV Data  |
+----------------------+
```

---

# 🛠️ Technologies Used

## ☕ Java
- Java 17+
- Collections Framework
- File Handling
- Java HTTP Client API

---

## 🐍 Python
- FastAPI
- PyTorch
- Pandas
- NumPy
- Scikit-learn
- Uvicorn

---

## 🧠 Machine Learning
- LSTM Neural Network
- Time-Series Forecasting
- Sequence Prediction

---

## 📚 Data Structures
- Binary Search Tree (BST)
- Stack
- Queue
- Arrays / Lists

---

# 📂 Project Structure

```text
HealthSense/
│
├── src/
│   └── HealthSense/
│       ├── Main.java
│       ├── ForecastService.java
│       ├── DiseaseReport.java
│       ├── BSTAnalyzer.java
│       ├── QueueManager.java
│       └── UndoStack.java
│
├── python_ml/
│   ├── main.py
│   ├── owid-covid-data.csv
│   ├── lstm_forecast.py
│   └── requirements.txt
│
└── README.md
```

---

# ⚙️ Setup Instructions

## 1️⃣ Clone Repository

```bash
git clone https://github.com/InzamanCareem/HealthSense.git
cd HealthSense 
```

## 2️⃣ Setup Python Forecast Server
### Install Dependencies

```bash
pip install -r requirements.txt
```

### Run FastAPI Server

```bash
uvicorn main:app
```

### Server will start at:

http://127.0.0.1:8000

## 3️⃣ Run Java Application

### Compile
```bash
javac Main.java
```

### Run
```bash
java Main
```

# 🔮 Future Improvements
## Planned Features

- Real-time disease APIs
- Live dashboard visualization
- Database integration
- Multi-disease forecasting
- Authentication system
- Advanced AI forecasting
- Heatmap outbreak visualization

# 📜 License

This project is licensed under the MIT License.
