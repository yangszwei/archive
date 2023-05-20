import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.impute import SimpleImputer

source_data = pd.read_csv('kc_house_data_NaN_new.csv')

kc_house_data = source_data[['price', 'bedrooms', 'bathrooms', 'sqft_living', 'sqft_lot', 'floors', 'yr_built', 'zipcode', 'yr_old']]

kc_house_data = kc_house_data.drop('price', axis=1)

y = source_data['price']

x_train, x_test, y_train, y_test = train_test_split(kc_house_data, y, test_size=0.2, random_state=0)

print('訓練集的筆數:', len(x_train))
print('測試集的筆數:', len(x_test))
print('測試集佔整體資料的比例:', len(x_test) / (len(x_train) + len(x_test)))

# impute data
imputer = SimpleImputer(missing_values=np.nan, strategy='mean')
imputed_x_train = imputer.fit_transform(x_train)

reg = LinearRegression().fit(imputed_x_train, y_train)

# The intercept
print("intercept: \n %.2f" % reg.intercept_) ## y=ax+b
# The coefficients
print("Coefficients: \n " , reg.coef_)

pd.DataFrame(zip(pd.DataFrame(imputed_x_train), reg.coef_), columns=['變數', '係數']).\
    sort_values(by='係數', ascending=False)

from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline

model_pl = make_pipeline(StandardScaler(), LinearRegression())
model_pl.fit(imputed_x_train, y_train)

reg = model_pl.named_steps['linearregression']

pd.DataFrame(zip(pd.DataFrame(imputed_x_train), reg.coef_), columns=['變數','係數']).\
    sort_values(by='係數', ascending=False)

from sklearn.metrics import mean_squared_error, mean_absolute_error, r2_score

imputed_x_test = imputer.transform(x_test)

y_pred = model_pl.predict(imputed_x_test)

print('Mean Squred Error:',mean_squared_error(y_test, y_pred))
print('Mean Absolute Error:', mean_absolute_error(y_test, y_pred))
print('R2 Score:', r2_score(y_test, y_pred))

print(y_train[0])
print(model_pl.predict(imputed_x_train[[0]]))
