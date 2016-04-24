int orden = 0;   // for incoming serial data

void setup() {
        pinMode(13,OUTPUT);
        Serial.begin(9600);    
}

void loop() {

        // send data only when you receive data:
        if (Serial.available() > 0) {
                // read the incoming byte:
                orden = Serial.read();

                if (orden == 1)
                  digitalWrite(13,HIGH);
        }
}
