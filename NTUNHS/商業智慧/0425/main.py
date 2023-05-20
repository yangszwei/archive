import matplotlib.pyplot as plt
import pandas as pd

# Exercise 1

date_range = pd.date_range("2022-04-08", periods=7)
book_sold = pd.Series([30, 25, 39, 40, 28, 30, 42], index=date_range)
price = pd.Series([400, 250, 570, 420, 350, 380, 520], index=date_range)

book_sales = pd.DataFrame({"BookSold": book_sold, "Price": price, "Amount": book_sold * price})
print(book_sales)

# Exercise 2

days = pd.Series(list(map(lambda x: f"Day {x}", range(1, 6))))

bikes = pd.DataFrame({
    "Enfield": pd.Series([50, 40, 70, 80, 20], index=days),
    "Honda": pd.Series([80, 20, 20, 50, 60], index=days),
    "Yamaha": pd.Series([70, 20, 60, 40, 60], index=days),
    "KTM": pd.Series([85, 35, 20, 70, 50], index=days),
})

# (1) line chart

bikes.plot(kind="line",
           title="bike details in line plot",
           xlabel="Days",
           ylabel="Distance covered in kms",
           color=["green", "lightblue", "purple", "yellow"])

plt.show()

# (2) bar chart

average = bikes.mean(axis=0)
average.plot(kind="bar",
             title="bike details in bar plot",
             xlabel="Bikes",
             ylabel="Average distance covered in kms",
             color=["green", "lightblue", "purple", "yellow"])

plt.show()

# (3) scatter plot

ax = plt.gca()
ax.set_title("bike details in scatter plot")
ax.set_xlabel("Bikes")
ax.set_ylabel("Average distance covered in kms")
ax.scatter(average.index, average.values, color=["green", "lightblue", "purple", "yellow"])

plt.show()
