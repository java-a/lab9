# lab9
## Project 1 Review

There are three sample project 1 implementations in the repository of this lab. 

Choose **one project** and write a code review with the following requirements:

1. Read the project carefully and understand the code.
2. Briefly describe the functions of at least 3 key variables and 3 key methods in the project, like what you wrote in your project document.
3. Explain how the project implements undo and redo.
4. Explain how the project implements the winning case that one player will lose if all its animals can't move.
5. Demonstrate how can you improve your code by reading the sample code.

The code written by `李向民` is similar to most students' code, with one class and several methods. It's recommended to review his code if you have some problems with undo, redo and the last winning case. 

The code written by `邱轶扬` has one more class: `Animal`. This is the first step to write the project in object-oriented way. His code is a little bit hard to understand. It's recommended to review his code if you got a 95+ score in PJ1.

The code written by `李逢双` is highly object-oriented. You can review TA's code if you want to challenge yourself. 

## Geometry: n-sided regular polygon

> Dictionary is at the end of this section. (关键单词的翻译在末尾)

In an n-sided regular polygon all sides have the same length and all angles have the same degree (i.e., the polygon is both equilateral and equiangular). **Design a class named `RegularPolygon` that contains:**

- A private `int` data field named `n` that defines the number of sides in the polygon with default value 3. (一个定义了多边形边数的私有`int`型变量`n`，默认值为3。)

- A private `double` data field named `side` that stores the length of the side with default value 1.

- A private `double` data field named `x` that defines the x-coordinate of the center of the polygon with default value `0`.

- A private `double` data field named `y` that defines the y-coordinate of the center of the polygon with default value `0`.

- A no-arg constructor that creates a regular polygon with default values. (一个没有参数的构造函数，用默认值创建正多边形。)

- A constructor that creates a regular polygon with the specified number of sides and length of side, centered at (`0`, `0`).

- A constructor that creates a regular polygon with the specified number of sides, length of side, and x-and y-coordinates.

- The accessor and mutator methods for all data fields.

- The method `getPerimeter()` that returns the perimeter of the polygon.

- The method `getArea()` that returns the area of the polygon. The formula for computing the area of a regular polygon is

  ![](https://cloud.githubusercontent.com/assets/7262715/20463350/7bb2516e-af6b-11e6-9554-c020b42ad7b4.png)

  where `n` stands for number of sides, `s` for length of side and `p` equals to `π`.

  **Design and implement the class.** The `RegularPolygon` class should **not** contain a `main` method. Write a test program that creates three `RegularPolygon` objects, created using the no-arg constructor, using `RegularPolygon(6, 4)`, and using `RegularPolygon(10, 4, 5.6, 7.8)`. For each object, display its perimeter and area.

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
>
> implement: 实现
>
> formula: 公式

## 提交

Lab Deadline为本周二晚23:59:59。

将代码打包，以`学号_姓名.文件类型`的格式命名，如`13302010039_童仲毅.zip`。上传至FTP：

```
ftp://10.132.141.33/classes/16/161 程序设计A （戴开宇）/WORK_UPLOAD/lab9
```

PJ1的Code Review文档Deadline为本周五晚23:59:59，上传至：

```
ftp://10.132.141.33/classes/16/161 程序设计A （戴开宇）/WORK_UPLOAD/Code Review/pj1
```