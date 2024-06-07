import scala.io.Source
import scala.collection.mutable.ListBuffer

case class DataFrame(data: List[List[Any]], columns: List[String]) {
  def select(cols: List[String]): DataFrame = {
    val indices = cols.map(columns.indexOf)
    val newData = data.map(row => indices.map(row.apply))
    DataFrame(newData, cols)
  }

  def filter(predicate: List[Any] => Boolean): DataFrame = {
    DataFrame(data.filter(predicate), columns)
  }

  def addColumn(name: String, values: List[Any]): DataFrame = {
    val newData = data.zip(values).map { case (row, value) => row :+ value }
    DataFrame(newData, columns :+ name)
  }

  def dropColumn(name: String): DataFrame = {
    val index = columns.indexOf(name)
    val newData = data.map(row => row.take(index) ++ row.drop(index + 1))
    DataFrame(newData, columns.filter(_ != name))
  }

  def fillNA(value: Any): DataFrame = {
    val newData = data.map(row => row.map(elem => if (elem == null) value else elem))
    DataFrame(newData, columns)
  }

  def groupBy(cols: List[String])(agg: List[Any] => Any): Map[List[Any], Any] = {
    val indices = cols.map(columns.indexOf)
    data.groupBy(row => indices.map(row.apply)).mapValues(agg)
  }

  def join(other: DataFrame, joinType: String, leftCol: String, rightCol: String): DataFrame = {
    val leftIndex = columns.indexOf(leftCol)
    val rightIndex = other.columns.indexOf(rightCol)

    val joinedData = joinType match {
      case "inner" =>
        data.flatMap { leftRow =>
          other.data.flatMap { rightRow =>
            if (leftRow(leftIndex) == rightRow(rightIndex))
              Some(leftRow ++ rightRow)
            else
              None
          }
        }
      // Implement other join types similarly
    }

    DataFrame(joinedData, columns ++ other.columns)
  }

  def show(): Unit = {
    val header = columns.mkString(", ")
    val rows = data.map(_.mkString(", ")).mkString("\n")
    println(header)
    println(rows)
  }
}

object DataFrameReader {
  def csv(path: String, inferSchema: Boolean = true): DataFrame = {
    val lines = Source.fromFile(path).getLines().toList
    val columns = lines.head.split(",").toList
    val data = lines.tail.map { line =>
      line.split(",").toList.map { value =>
        if (inferSchema) {
          if (value.forall(_.isDigit))
            value.toInt
          else if (value.forall(c => c.isDigit || c == '.'))
            value.toDouble
          else if (value.toLowerCase == "true" || value.toLowerCase == "false")
            value.toBoolean
          else
            value
        } else {
          value
        }
      }
    }
    DataFrame(data, columns)
  }
}

// Usage example
val df = DataFrameReader.csv("data.csv")
df.show()

val selectedDf = df.select(List("column1", "column2"))
selectedDf.show()

val filteredDf = df.filter(row => row(0).toString.startsWith("A"))
filteredDf.show()

val dfWithNewColumn = df.addColumn("newColumn", List(1, 2, 3))
dfWithNewColumn.show()

val dfWithoutColumn = df.dropColumn("column1")
dfWithoutColumn.show()

val filledDf = df.fillNA(0)
filledDf.show()

val groupedResult = df.groupBy(List("column1"))(_.map(_.apply(2).toString.toDouble).sum)
println(groupedResult)

val joinedDf = df.join(selectedDf, "inner", "column1", "column1")
joinedDf.show()
