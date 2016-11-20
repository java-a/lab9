# lab9
>  Midterm Review

## Project 1 Review

>  TODO

## Midterm Exam Review

> TODO

## Geometry: n-sided regular polygon

> Dictionary is at the end of this section.

In an n-sided regular polygon all sides have the same length and all angles have the same degree (i.e., the polygon is both equilateral and equiangular). Design a class named `RegularPolygon` that contains:

- A private `int` data field named `n` that defines the number of sides in the polygon with default value 3.

- A private `double` data field named `side` that stores the length of the side with default value 1.

- A private `double` data field named `x` that defines the x-coordinate of the center of the polygon with default value `0`.

- A private `double` data field named `y` that defines the y-coordinate of the center of the polygon with default value `0`.

- A no-arg constructor that creates a regular polygon with default values.

- A constructor that creates a regular polygon with the specified number of sides and length of side, centered at (`0`, `0`).

- A constructor that creates a regular polygon with the specified number of sides, length of side, and x-and y-coordinates.

- The accessor and mutator methods for all data fields.

- The method `getPerimeter()` that returns the perimeter of the polygon.

- The method `getArea()` that returns the area of the polygon. The formula for computing the area of a regular polygon is

  ![](https://cloud.githubusercontent.com/assets/7262715/20463350/7bb2516e-af6b-11e6-9554-c020b42ad7b4.png)

  where `n` stands for number of sides, `s` for length of side and `p` equals to `2π`.

  Design and implement the class. The `RegularPolygon` class should **not** contain a `main` method. Write a test program that creates three `RegularPolygon` objects, created using the no-arg constructor, using `RegularPolygon(6, 4)`, and using `RegularPolygon(10, 4, 5.6, 7.8)`. For each object, display its perimeter and area.

  The test program should look like:

  ```java
  public class TestRegularPolygon {
      public static void main(String[] args) {
          // Invoke test methods
        	...
      }

      private static boolean testWithNoArg() {
          ...
      }

      private static boolean testWithNumberAndLength() {
          ...
      }

      private static boolean testWithNumberLengthAndCoordinates() {
          ...
      }
  }
  ```


> **Dictionary:**
>
> polygon: 多边形
>
> regular polygon: 正多边形
>
> equilateral: 等边
>
> equiangular: 等角
>
> coordinate: 坐标
>
> accessor methods: 数据字段的访问方法，指`getter`
>
> mutator methods: 数据字段的改变方法，指`setter`
>
> perimeter: 周长

## 提交

Deadline为`2016.11.22 23:59:59(UTC+8)`。

将代码打包，以`学号_姓名.文件类型`的格式命名，如`13302010039_童仲毅.zip`。上传至FTP：

```
ftp://10.132.141.33/classes/16/161 程序设计A （戴开宇）/WORK_UPLOAD/lab9
```

PJ1的Code Review文档请上传至：

```
ftp://10.132.141.33/classes/16/161 程序设计A （戴开宇）/WORK_UPLOAD/Code Review/pj1
```