import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Exercise :
# 1. 從隨身碟讀進 iris.csv 資料

iris = pd.read_csv('iris.csv')

# 2. 繪出 Species 的直方圖

sns.histplot(data=iris, x='Species').set_title('Species\'s histogram')
plt.show()

# 3. 以 SepalLengthCm 與 SepalWidthCm 繪出資料的散佈圖

sns.scatterplot(x='SepalLengthCm', y='SepalWidthCm', data=iris)\
    .set_title('SepalLengthCm & SepalWidthCm\'s Scatterplot')
plt.show()

# 4. 以 PetalLengthCm 與 PetalWidthCm 繪出資料散佈圖

sns.scatterplot(x='PetalLengthCm', y='PetalWidthCm', data=iris, hue='Species').set_title(
    'PetalLengthCm & PetalWidthCm\'s Scatterplot')
plt.show()

# 5. 計算 3 種花各有多少數量，繪出 3 種花種數量的方塊圖

iris['Species'].value_counts().plot(kind='bar').set_title('Bar grapg of count of each Species')
plt.show()

# Exercise :
# 1. 從隨身碟讀進 insurance.csv

insurance = pd.read_csv('insurance.csv')

# 2. 繪出 age 的直方圖

sns.histplot(data=insurance, x='age').set_title('age\'s histogram')
plt.show()

# 3. 繪出 children 的直方圖

sns.histplot(data=insurance, x='children').set_title('children\'s histogram')
plt.show()

# 4. 繪出 bmi 的直方圖

sns.histplot(data=insurance, x='bmi').set_title('bmi\'s histogram')
plt.show()

# 5. 印出以上各特徵值的相關係數

df = insurance[['age', 'children', 'bmi']]
print(df.corr())

# 6. 繪製 Heatmap

sns.heatmap(df.corr(), annot=True).set_title('Correlation coefficient')
plt.show()

# 7. 印出以上各特徵值與 charges 的相關係數

df = insurance[['age', 'children', 'bmi', 'charges']]
print(df.corr())

# 8. 繪製 Heatmap

sns.heatmap(df.corr(), annot=True).set_title('Correlation coefficient with charges')
plt.show()

# 9. 分別繪製各特徵值之 Box graph

sns.boxplot(data=df.corr()).set_title('Box graph of age')
plt.show()
