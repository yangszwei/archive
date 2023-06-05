import pandas as pd
import matplotlib.pyplot as plt
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsClassifier
from sklearn.pipeline import make_pipeline
from sklearn.metrics import confusion_matrix, accuracy_score, classification_report

# Load Data

social_network_ads = pd.read_csv('Social_Network_Ads.csv')
le = preprocessing.LabelEncoder()

# Preprocessing

social_network_ads['Gender'] = le.fit_transform(social_network_ads['Gender'])

# Build Model

X_cols = ['Gender', 'Age', 'EstimatedSalary']
y_col = 'Purchased'
X = social_network_ads[X_cols]
y = social_network_ads[y_col]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=42)

print('訓練集的筆數：', len(X_train))
print('測試集的筆數：', len(X_test))
print('測試集所佔全部資料的百分比：', len(X_test) / len(X))

# Training

model = make_pipeline(StandardScaler(), KNeighborsClassifier())

model.fit(X_train, y_train)

print('測試集的正確率：', model.score(X_test, y_test))

y_pred = model.predict(X_test)
print('正確率：', round(accuracy_score(y_test, y_pred), 2))

print('混淆矩陣', confusion_matrix(y_test, y_pred), sep='\n')
print('綜合報告', classification_report(y_test, y_pred), sep='\n')

# Neighbors - Accuracy Plot

accuracy = []

for i in range(3, 10):
    model = make_pipeline(StandardScaler(), KNeighborsClassifier(n_neighbors=i))
    model.fit(X_train, y_train)
    print(f"(n = {i}) => accuracy = {round(model.score(X_test, y_test), 2)}")
    accuracy.append(accuracy_score(y_test, model.predict(X_test)))

plt.plot(range(3, 10), accuracy)
plt.show()
