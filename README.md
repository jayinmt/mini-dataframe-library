# Mini-Scala DataFrame Library

The Mini-Scala DataFrame Library is a lightweight and efficient implementation of a DataFrame library in Scala, inspired by Pandas in Python and Spark DataFrame. It provides core functionalities for data manipulation, filtering, grouping, and aggregation, with a focus on performance and scalability.

## Features

- Data Loading and Saving:
  - Read and write CSV files
  - Support for basic data types (Int, Double, String, Boolean)

- DataFrame Operations:
  - Select columns
  - Filter rows based on conditions
  - Add new columns with derived values
  - Drop columns
  - Handle missing values (drop or fill)

- Aggregations and Grouping:
  - Sum, average, min, max, and count operations
  - Group by functionality to perform operations on grouped data

- Join Operations:
  - Perform inner, outer, left, and right joins

- Performance Optimization:
  - Utilize Scala's advanced features such as lazy evaluation, immutability, and parallel collections for performance optimization

## Getting Started

### Prerequisites

- Scala 2.13.x
- sbt (Scala Build Tool)

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/jaydxyz/mini-dataframe-library.git
   ```

2. Navigate to the project directory:
   ```
   cd mini-scala-dataframe
   ```

3. Compile the project:
   ```
   sbt compile
   ```

### Usage

1. Import the necessary classes in your Scala script or application:
   ```scala
   import com.example.dataframe.{DataFrame, DataFrameReader}
   ```

2. Load data from a CSV file:
   ```scala
   val df = DataFrameReader.csv("data.csv")
   ```

3. Perform DataFrame operations:
   ```scala
   val selectedDf = df.select(List("column1", "column2"))
   val filteredDf = df.filter(row => row(0).toString.startsWith("A"))
   val dfWithNewColumn = df.addColumn("newColumn", List(1, 2, 3))
   val dfWithoutColumn = df.dropColumn("column1")
   val filledDf = df.fillNA(0)
   ```

4. Perform aggregations and grouping:
   ```scala
   val groupedResult = df.groupBy(List("column1"))(_.map(_.apply(2).toString.toDouble).sum)
   ```

5. Perform join operations:
   ```scala
   val joinedDf = df.join(selectedDf, "inner", "column1", "column1")
   ```

6. Display the DataFrame:
   ```scala
   df.show()
   ```


## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

- [Pandas](https://pandas.pydata.org/) - Inspiration for the DataFrame library
- [Spark](https://spark.apache.org/) - Inspiration for the DataFrame library
