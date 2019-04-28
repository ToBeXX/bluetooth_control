/*******************************************************************************
手机APP蓝牙控制四个LED灯开关的程序
*******************************************************************************/
#define led2 2
#define led3 3
#define led4 4
#define led5 5
byte ch;

void setup()
{

  Serial.begin(9600);		//波特率为9600
//需要用到的引脚的定义 定义为输出的引脚
        pinMode(led2, OUTPUT);
          pinMode(led3, OUTPUT);
            pinMode(led4, OUTPUT);
              pinMode(led5, OUTPUT);
}

void loop()
{


    if ( Serial.available() )

  {
//串口数据的接收 与数据的判断
    ch = Serial.read();

    if ( ch == 'b' )//接收到的数据为b时  将灯1关闭

    {

      digitalWrite(led2,HIGH);

    }


        if ( ch == 'a' )//当接收到的数据为1时  将灯1打开

    {

      digitalWrite(led2,LOW);

    }


    
    if ( ch == 'd' )//接收到的数据为d时  将灯2关闭

    {

      digitalWrite(led3,HIGH);

    }


        if ( ch == 'c' )//接收到的数据为c时 将灯2打开

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


    
    if ( ch == 'h' )//接收到的数据为h的时候  将灯4关闭

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

}
