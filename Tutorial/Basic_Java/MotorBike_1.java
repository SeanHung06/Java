public class MotorBike_1 {
    private int speed;

    MotorBike_1(){
        this(5);
    }
    MotorBike_1(int speed){
        if (speed > 0){
            this.speed = speed;
        }
    }
}
