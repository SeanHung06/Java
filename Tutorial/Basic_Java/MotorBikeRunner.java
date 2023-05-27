public class MotorBikeRunner {
    public static void main (String[] args) {
        MotorBike ducati = new MotorBike();
        MotorBike honda = new MotorBike();
        MotorBike suzuki = new MotorBike();

        ducati.start();
        honda.start();
        suzuki.start();

        ducati.setSpeed(-100);
        suzuki.setSpeed(60);
        System.out.println(ducati.getSpeed());
        System.out.println(honda.getSpeed());
        System.out.println(suzuki.getSpeed());
        ducati.increaseSpeed(2000);
        honda.increaseSpeed(200);
        System.out.println(ducati.getSpeed());
        System.out.println(honda.getSpeed());
        System.out.println(suzuki.getSpeed());
    }
}
