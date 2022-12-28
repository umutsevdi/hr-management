import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn import preprocessing 
from sklearn.svm import SVR
import pickle

model = pickle.load(open("SVRmodel", 'rb'))
scaler = pickle.load(open("scaler", 'rb'))
lbe = pickle.load(open("laberEnacoder", 'rb'))

columns = ['working_hour', 'sprint', 'sprint_tasks_awaiting', 'tasks_completed','tasks_delayed', 'tasks_incomplete', 
           'team_score_avg', 'title']
df = pd.read_csv("new_data.csv")
ids = df['e_id']
df = df[columns]


#label encoding
df["title"] = lbe.transform(df["title"])
#scaling
df = pd.DataFrame(scaler.transform(df), columns = columns)

y_pred = y_pred.astype(int)
enf = 0.5
y_pred = y_pred * (1+enf)
df = pd.DataFrame({'e_id': ids, 'salary': y_pred}, columns=['e_id', 'salary'])
pd.DataFrame(df).to_json("result.json")
