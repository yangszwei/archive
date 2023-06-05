import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import confusion_matrix, accuracy_score, classification_report, \
                            precision_recall_curve, recall_score
from sklearn.metrics import precision_score, recall_score, roc_curve

# Load Data

titanic_train = pd.read_csv('titanic_train.csv')
le = preprocessing.LabelEncoder()

# Preprocessing

titanic_train = titanic_train[['Survived', 'Pclass', 'Sex', 'Age']]
titanic_train['Sex'] = le.fit_transform(titanic_train['Sex'])
titanic_train['Age'] = titanic_train['Age'].fillna(titanic_train['Age'].median())

# Training

X = titanic_train[['Pclass', 'Sex', 'Age']]
y = titanic_train['Survived']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=42)

model = LogisticRegression()
model.fit(X_train, y_train)

# Testing

y_pred = model.predict(X_test)

cm = confusion_matrix(y_test, y_pred)

print(pd.DataFrame(cm, columns=['預測 0', '預測 1'], index=['實際 0', '實際 1']))
print('整體正確率：', round(accuracy_score(y_test, y_pred), 2))
print(classification_report(y_test, y_pred))

# Precision Score

scores = []

y_pred_proba = model.predict_proba(X_test)[:, 1]

for threshold in np.arange(0, 1, 0.1):
    y_pred = np.where(y_pred_proba >= threshold, 1, 0)
    prec = precision_score(y_test, y_pred, pos_label=1)
    recall = recall_score(y_test, y_pred, pos_label=1)
    scores.append([threshold, prec, recall])

pr = pd.DataFrame(scores, columns=['門檻', '精確率', '召回率'])
pr.sort_values(by='門檻')

ax = pr.plot(x='召回率', y='精確率', marker='o')
ax.set_xlabel('Class 1 Recall')
ax.set_ylabel('Class 1 Precision')

for idx in pr.index:
    ax.text(x=pr.loc[idx, '召回率'], y=pr.loc[idx, '精確率'] - 0.02, s=pr.loc[idx, '門檻'].round(1))

plt.show()

# Precision Recall Curve

prec, recall, thres = precision_recall_curve(y_test, y_pred_proba, pos_label=1)
pr = pd.DataFrame(zip(thres, prec, recall), columns=['門檻', '精確率', '召回率'])

ax = pr.plot(x='召回率', y='精確率', marker='o')

for idx in pr.index:
    ax.text(x=pr.loc[idx, '召回率'], y=pr.loc[idx, '精確率'] - 0.02, s=pr.loc[idx, '門檻'].round(2))

plt.show()

# ROC Curve

scores = []

y_pred_proba = model.predict_proba(X_test)[:, 1]

for threshold in np.arange(0, 1, 0.1):
    y_pred = np.where(y_pred_proba >= threshold, 1, 0)
    prec = precision_score(y_test, y_pred, pos_label=1)
    recall = recall_score(y_test, y_pred, pos_label=1)
    scores.append([threshold, prec, recall])

roc = pd.DataFrame(scores, columns=['門檻', '精確率', '召回率'])
roc['1-特異度'] = 1 - roc['精確率']
roc.sort_values(by='門檻').head()

fpr, tpr, thres = roc_curve(y_test, y_pred_proba, pos_label=1)
df_roc = pd.DataFrame(zip(thres, fpr, tpr), columns=['門檻','1-特異度','敏感度'])

ax = df_roc.plot(x='1-特異度', y='敏感度', marker='o')

for idx in df_roc.index:
    ax.text(x=df_roc.loc[idx,'1-特異度'], y=df_roc.loc[idx,'敏感度']-0.05, s=df_roc.loc[idx,'門檻'].round(2))

plt.show()
