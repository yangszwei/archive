import numpy as np
from sklearn.linear_model import LinearRegression

import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.impute import SimpleImputer

# 1. 進入 https://data.gov.tw/dataset/40448

air_quality = pd.read_csv('aqx_p_432.csv', encoding='utf-8', index_col='sitename')

print(air_quality.size)

# 2. 下載 air quality 資料，印出下表資欄位資料

print(air_quality[['aqi', 'co', 'o3', 'pm10', 'pm2.5', 'no2', 'no']].head(10))

# 3.

# 4. 繪製個 aqi 及 6 空氣指標之 heatmap 如下

sns.heatmap(air_quality[['aqi', 'o3', 'pm10', 'pm2.5', 'no2']].corr(), annot=True, cmap='coolwarm')
plt.show()

# 5 建立 (aqi,o3,pm10,pm2.5) 這幾個特徵值的 pairplot 如下

sns.pairplot(air_quality[['aqi', 'o3', 'pm10', 'pm2.5']])
plt.show()

# 6 建立 pm 2.5 與 aqi 的 預測 model ，呈現出模型(方程式)，並繪製 regression line。預測當
# pm2.5 為 20， 則 aqi 為多少?

imputer = SimpleImputer(missing_values=np.nan, strategy='mean')
air_quality[['pm2.5']] = imputer.fit_transform(air_quality[['pm2.5']])
air_quality[['aqi']] = imputer.fit_transform(air_quality[['aqi']])

reg = LinearRegression()
reg.fit(air_quality[['pm2.5']].to_numpy(), air_quality[['aqi']].to_numpy())
print('AQI = ', reg.coef_, '* PM2.5 + ', reg.intercept_)
print('AQI(20) = ', reg.predict([[20]]))

sns.lmplot(x='pm2.5', y='aqi', data=air_quality)
plt.show()

# 7. 建立 o3 與 aqi 的 預測 model ，並繪製 regression line

imputer = SimpleImputer(missing_values=np.nan, strategy='mean')
air_quality[['o3']] = imputer.fit_transform(air_quality[['o3']])
air_quality[['aqi']] = imputer.fit_transform(air_quality[['aqi']])

reg = LinearRegression()
reg.fit(air_quality[['o3']].to_numpy().reshape(-1, 1), air_quality[['aqi']].to_numpy().reshape(-1, 1))
print('AQI = ', reg.coef_, '* O3 + ', reg.intercept_)

sns.lmplot(x='o3', y='aqi', data=air_quality)
plt.show()
