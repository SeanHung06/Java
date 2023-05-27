public class MotorBike {
    private int speed;
    public void start() {
        System.out.println("Bike started");
    }
    public void setSpeed(int speed) {
        if (speed > 0){
            this.speed = speed;
        }
    }
    public void increaseSpeed(int howMuch) {
        this.speed = this.speed + howMuch;
    }
    
    int getSpeed() {
        return this.speed;
    }
    
}
