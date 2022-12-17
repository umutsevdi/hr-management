{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": 1,
   "id": "4845d84c",
   "metadata": {},
   "outputs": [],
   "source": [
    "import pandas as pd\n",
    "import numpy as np\n",
    "from sklearn.preprocessing import LabelEncoder\n",
    "from sklearn import preprocessing \n",
    "from sklearn.svm import SVR\n",
    "import pickle\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 2,
   "id": "93de08b1",
   "metadata": {},
   "outputs": [],
   "source": [
    "model = pickle.load(open(\"SVRmodel\", 'rb'))\n",
    "scaler = pickle.load(open(\"scaler\", 'rb'))\n",
    "lbe = pickle.load(open(\"laberEncoder\", 'rb'))\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 3,
   "id": "6480d644",
   "metadata": {},
   "outputs": [],
   "source": [
    "columns = ['working_hour', 'sprint', 'sprint_tasks_awaiting', 'tasks_completed','tasks_delayed', 'tasks_incomplete', \n",
    "           'team_score_avg', 'title']\n",
    "df = pd.read_csv(\"new_data.csv\")\n",
    "df = df[columns]\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 4,
   "id": "75d062b4",
   "metadata": {},
   "outputs": [],
   "source": [
    "#label encoding\n",
    "df[\"title\"] = lbe.transform(df[\"title\"])"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 5,
   "id": "3c8c1313",
   "metadata": {},
   "outputs": [],
   "source": [
    "#scaling\n",
    "df = pd.DataFrame(scaler.transform(df), columns = columns)\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 6,
   "id": "06c02a79",
   "metadata": {},
   "outputs": [],
   "source": [
    "y_pred = model.predict(df)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 16,
   "id": "cb98bf53",
   "metadata": {},
   "outputs": [],
   "source": [
    "y_pred = y_pred.astype(int)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 18,
   "id": "9ad08fce",
   "metadata": {},
   "outputs": [],
   "source": [
    "pd.DataFrame(y_pred).to_json(\"result.json\")\n"
   ]
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3 (ipykernel)",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.10.9"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 5
}
