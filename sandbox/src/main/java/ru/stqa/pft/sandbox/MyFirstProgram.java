package ru.stqa.pft.sandbox;



public class MyFirstProgram {
    public static double distance(Point p1, Point p2){

        return Math.sqrt(p2.x - p1.x) + Math.sqrt(p2.y - p1.y);
    }

    public static void main(String[] args) {
        Point pointOne = new Point(8.4, 8.7);
        Point pointTwo = new Point(9.5, 9.7);

        System.out.println("!!!!!!!!!!");
        System.out.println("Вызов функции из класса MyFirstProgram");
        System.out.println(distance(pointOne, pointTwo));

        System.out.println("!!!!!!!!!!");
        System.out.println("Вызов метода из класса Point");
        System.out.println(pointOne.distance(pointTwo));
    }
}
