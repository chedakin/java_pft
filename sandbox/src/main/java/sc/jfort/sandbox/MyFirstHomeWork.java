package sc.jfort.sandbox;

public class MyFirstHomeWork {
    public static void main(String[] args){
        Point p1 = new Point(-1,9);
        Point p2 = new Point (2,5);

        System.out.println("Точка P1 имеет координаты: x = " + p1.x + " , y = " + p1.y);
        System.out.println("Точка P2 имеет координаты: x = " + p2.x + " , y = " + p2.y);
        System.out.println("Расстояния между точками равно " + distance(p1, p2));
        System.out.println("Расстояния от p1 до p2 равно " + p1.distanceToP(p2));
    }

    public static double distance (Point p1, Point p2){
        double dist = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y),2));
        return dist;

    }
}
