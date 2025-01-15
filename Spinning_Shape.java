// javac SpinningDonut. java
// java SpinningDonut
public class Spinning_Shape {

    public static void main(String[] args) throws
            InterruptedException {
        // Donut parameters
        int width = 40;
        int height = 22;

        // Rotation angles about x and z
        double A = 0;
        double B = 0;

        // Scale of shape
        double K1 = 15;
        double K2 = 5;

        // Shape change - Radii
        double R1 = 1;
        double R2 = 2;

        // Resolution & Brightness
        double theta_spacing = 0.07;
        double phi_spacing = 0.02;
        char[] brightness = ".,-°:;=! *#$@".toCharArray();

// Loop to keep the donut spinning - Condition is always true
        while (true) {
            double[] z = new double[width * height]; // depth of each "pixel" | sacrifice intuition for memory conservation
            char[] b = new char[width * height]; // brightness of all "pixels" | sacrifice intuition for memory conservtion

            for (int i = 0; i < z.length; i++) {
                // Blacks out the entire screen
                z[i] = 0;
                b[i] = ' ';
            }

            for (double theta = 0; theta < 2 * Math.PI; theta += theta_spacing)
                {
                for (double phi = 0; phi < 2 * Math.PI; phi += phi_spacing)
                {
                    double cosTheta = Math.cos(theta);
                    double sinTheta = Math.sin(theta);
                    double cosPhi = Math.cos(phi);
                    double sinPhi = Math.sin(phi);
                    double cosA = Math.cos(A);
                    double sinA = Math.sin(A);
                    double cosB = Math.cos(B);
                    double sinB = Math.sin(B);

                    // Establish the definition of our object in parametric components -> change to another shape.
                    double circleX = R2 + R1 * cosTheta;
                    double circleY = R1 * sinTheta;

                    // 3D coordinates after rotation - Establish motion
                    double x = circleX * (cosB * cosPhi + sinA * sinB * sinPhi) - circleY * cosA * sinB;
                    double y = circleX * (sinB * cosPhi - sinA * cosB * sinPhi) + circleY * cosA * cosB;
                    double zPos = K2 + cosA * circleX * sinPhi + circleY * sinA;
                    double ooz = 1 / zPos; // One over Z (depth)
                    // Establish x and y components
                    int xp = (int) (width / 2 + K1 * ooz * x);
                    int yp = (int) (height / 2 - K1 * ooz * y);
                    int idx = xp + yp * width;

                    double luminance = cosPhi * cosTheta * sinB - cosA * cosTheta * sinPhi - sinA * sinTheta + cosB * (cosA * sinTheta -
                            cosTheta * sinPhi * sinA);
                    if (idx >= 0 && idx < width * height && ooz >
                            z[idx]) {
                        z[idx] = ooz;
                        int luminanceIndex = (int) (8 * luminance);
                        if (luminanceIndex > 0) {
                            b[idx] = brightness[luminanceIndex];
                        }
                    }
                }
            }
// Print frame
            for (int k = 0; k < width * height; k++) {
                System.out.print(k % width != 0 ? b[k] : "\n");
            }

            A += 0.04;
            B += 0.02;

            Thread.sleep(50); //Adjust for the desired speed
        }
    }
}
