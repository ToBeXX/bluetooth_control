#include<Wire.h>
#include<RTClib.h>
#define led2 2
#define led3 3
#define led4 4
#define led5 5
int buffer[8];
char ch ;
int chc ;
int counter = 0 ;
RTC_DS1307 RTC;
void setup()
{
  Wire.begin();
  RTC.begin();
  Serial.begin(9600);    
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(led4, OUTPUT);        
  pinMode(led5, OUTPUT);
}
void loop(){
  DateTime now = RTC.now();
  if (Serial.available()){
    ch = Serial.read();
    Serial.println(ch);
    if(ch == 'a' | ch == 'b' | ch == 'd' | ch == 'e' | ch == 'f' | ch == 'g' | ch == 'h' | ch == 'c' | ch == 'o' | ch == 'x'){
      if ( ch == 'b' )
      {
        digitalWrite(led2,HIGH);
      }
      if ( ch == 'a' )
      {
        digitalWrite(led2,LOW);
      }
      if ( ch == 'd' )//接收到的数据为d时  将灯2关闭
      {
        digitalWrite(led3,HIGH);
      }
      if (ch == 'c' )//接收到的数据为c时 将灯2打开
      {
        digitalWrite(led3,LOW);
      }
      if ( ch == 'f' )//接收到的数据为f时候  将灯3关闭
      {
        digitalWrite(led4,HIGH);
      }
      if ( ch == 'e' )//接收到的数据为e的时候 将灯3打开
      {
        digitalWrite(led4,LOW);
      }
      if (ch == 'h' )//接收到的数据为h的时候  将灯4关闭
      {
        digitalWrite(led5,HIGH);
      }
      if ( ch == 'g' )//接收到的数据为g的时候   灯4打开
      {
        digitalWrite(led5,LOW);
      }
      if ( ch == 'x' )//当接收到的数据为x的时候灯全部的关闭
      {
        digitalWrite(led5,HIGH);
        digitalWrite(led4,HIGH);
        digitalWrite(led3,HIGH);
        digitalWrite(led2,HIGH);
      }
      if ( ch == 'o' )//当接收的数据为o的时候  灯全部的打开
      {
        digitalWrite(led5,LOW);
        digitalWrite(led4,LOW);
        digitalWrite(led3,LOW);
        digitalWrite(led2,LOW);
      }
    }
     if(ch != 'a' & ch != 'b' & ch != 'c' & ch != 'd' & ch != 'e' & ch != 'f' & ch != 'g' & ch != 'h' & ch != 'o' & ch != 'x'){
      chc = ch - '0';
      buffer[counter] = chc;
      counter = counter +1;
      Serial.println(counter);
      if(counter == 8){
        counter = 0;
        }
     }
  
 } 
if(now.hour()== buffer[0]*10 + buffer[1] & now.minute()== buffer[2]*10 + buffer[3]){
      digitalWrite(led5,HIGH);
      digitalWrite(led4,HIGH);
      digitalWrite(led3,HIGH);
      digitalWrite(led2,HIGH);
}
if(now.hour()== buffer[4]*10 + buffer[5] & now.minute()== buffer[6]*10 + buffer[7]){
      digitalWrite(led5,LOW);
      digitalWrite(led4,LOW);
      digitalWrite(led3,LOW);
      digitalWrite(led2,LOW);
}
}
