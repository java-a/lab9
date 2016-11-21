
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by qyy on 2016/9/29.
 */
public class Jungle {
    static int[][] animalMap = new int[7][9];    //存放动物地图
    static ArrayList[][] preAnimalMap = new ArrayList[7][9];    //存放历史动物地图
    static int[][] alAnimalMap = new int[7][9];      //存放全信息动物地图（包含动物的值以及阵营）
    static int[][] tileMap = new int[7][9];      //地形地图
    static int nowCamp = 1;    //现在准备移动的阵营（用1代表左阵营，用2代表右阵营）
    static int leftDead = 0;      //左方的动物死亡数
    static int rightDead = 0;     //右方的动物死亡数
    static int leftTrap = 0;      //左方被困死动物数
    static int rightTrap = 0;     //右方被困死动物数
    static int undoStep = 0;      //悔棋的步数
    static int step = 0;          //总步数
    static boolean isFirst = true;    //是否是开局
    static String[][] printMap = new String[7][9];   //存放用于打印的地图
    static Animal[] leftcamp = new Animal[9];    //左阵营的动物
    static Animal[] rightcamp = new Animal[9];    //右阵营的动物
    static Animal voidAni = new Animal(0, 0);     //一只空动物（值为0，阵营为0）


    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in); //定义输入器
        boolean isPlay = true;   //判断是否要继续玩下去
        help();    //打印帮助文档
        while (isPlay) {
            setAlValue();   //调用setAlValue方法
            readMap();    //调用readMap方法
            initAnimal();   //调用initAnimal方法
            isPlay = play(input);//开始游戏主体部分，返回是否继续玩耍的值
        }

    }

    public static boolean play(Scanner input) {

            String str = "";      //将输入值放置于此
            boolean isMove = false;    //此轮是否实质性的移动了呢（而不是输错，或是悔棋之类的）

            while (findAnimal(3, 0).getCamp() != 2 && findAnimal(3, 8).getCamp() != 1) {
                leftDead = 0;
                rightDead = 0;
                leftTrap = 0;
                rightTrap = 0;                  //设置每轮开始的初始值
                if (nowCamp == 1) {
                    if (isMove)
                        saveMap();                       //如果实质性移动了，保存地图至历史地图
                    transPrint();
                    mapPrint();                             //打印地图
                    System.out.println("左方玩家行动：");
                    str = input.nextLine();
                    if (str.equals("exit")) {
                        return false;                      //输入exit，返回false表示不再玩了
                    } else if (str.equals("undo")) {
                        undo();
                        isMove = false;
                    } else if (str.equals("redo")) {
                        redo();
                        isMove = false;
                    } else if (str.equals("restart")) {
                        return true;                       //输入restart，返回true表示还要玩
                    } else if (str.equals("help")) {
                        help();
                        isMove = false;
                    } else {
                        try {
                            isMove = camping(str, leftcamp);             //调用camping方法，返回是否实质性移动
                        } catch (Exception e) {
                            System.out.println("输入有误");                   //胡乱输入会出异常，输出“输入有误”
                            isMove = false;
                        }
                    }

                } else if (nowCamp == 2) {
                    if (isMove)
                        saveMap();                       //如果实质性移动了，保存地图至历史地图
                    transPrint();
                    mapPrint();                             //打印地图

                    System.out.println("右方玩家行动：");
                    str = input.nextLine();
                    if (str.equals("exit")) {
                        return false;
                    } else if (str.equals("undo")) {
                        undo();
                        isMove = false;
                    } else if (str.equals("redo")) {
                        redo();
                        isMove = false;
                    } else if (str.equals("restart")) {
                        step = undoStep = 0;
                        return true;
                    } else if (str.equals("help")) {
                        help();
                        isMove = false;
                    } else {
                        try {
                            isMove = camping(str, rightcamp);
                        } catch (Exception e) {
                            System.out.println("你的输入有误");
                            isMove = false;
                        }

                    }

                }

                isFirst = (step == undoStep) ? true : false;                    //判断是否位于开局位置
                updateMap();                                                    //更新地图

                for (Animal ing : leftcamp) {
                    if (!ing.getAlive() && ing.getValue() != 0) {
                        leftDead++;
                    }
                }

                for (Animal ing : rightcamp) {
                    if (!ing.getAlive() && ing.getValue() != 0) {
                        rightDead++;
                    }
                }                                                                //清点已死的动物数

                for (Animal ing : leftcamp) {
                    if (ing.getValue() == 1 && !ing.isRatMovable()) {
                        leftTrap++;
                    } else if ((ing.getValue() == 6 || ing.getValue() == 7) && !ing.isTiliMovable()) {
                        leftTrap++;
                    } else if (!ing.isMovable() && ing.getValue() != 0) {
                        leftTrap++;
                    }
                }

                for (Animal ing : rightcamp) {
                    if (ing.getValue() == 1 && !ing.isRatMovable()) {
                        rightTrap++;
                    } else if ((ing.getValue() == 6 || ing.getValue() == 7) && !ing.isTiliMovable()) {
                        rightTrap++;
                    } else if (!ing.isMovable() && ing.getValue() != 0) {
                        rightTrap++;
                    }
                }                                                                  //清点被困死的动物的数量

                if (leftDead == 8 || leftTrap == 8) {
                    nowCamp = 1;
                    break;
                } else if (rightDead == 8 || rightTrap == 8) {
                    nowCamp = 2;
                    break;
                }                                                                   //如若某方死亡动物数或困死数（包括已死）达到8，则跳出游戏的循环，终局
            }
            if (isMove)
                saveMap();                       //如果实质性移动了，保存地图至历史地图
            transPrint();
            mapPrint();                             //打印地图

            String need = "";                                                          //存放玩家需要
            if (nowCamp == 1) {
                System.out.println("右方胜！");
            } else if (nowCamp == 2) {
                System.out.println("左方胜！");
            }
            while (!need.equals("restart") && !need.equals("exit")) {
                System.out.println("输入restart重玩，输入exit退出：");
                need = input.nextLine();
                if (need.equals("restart")) {
                    return true;
                } else if (need.equals("exit")) {
                    return false;
                } else {
                    System.out.println("请输入有效命令：");
                }
            }

        return true;   //返回true表示要继续玩下去
    }                          //游戏主体玩耍方法

    public static void saveMap() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                preAnimalMap[i][j].add(step, alAnimalMap[i][j]);
            }
        }
    }                          //将地图存入历史地图方法

    public static void updateMap() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                if (findAnimal(i, j).getAlive()) {
                    animalMap[i][j] = findAnimal(i, j).getValue();
                    alAnimalMap[i][j] = animalMap[i][j] + 10 * findAnimal(i, j).getCamp();
                } else {
                    animalMap[i][j] = alAnimalMap[i][j] = 0;
                }
            }
        }
    }                        //更新地图，把位置属性为地图上对应坐标的动物值存入地图对应位置

    public static void preMap() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                alAnimalMap[i][j] = (int) preAnimalMap[i][j].get(step - undoStep);
            }
        }
    }                            //将历史地图中的对应step-undoStep的地图调出来，存入现在的地图中

    public static void setAlValue() {
        for (int i = 1; i <= 8; i++) {
            leftcamp[i] = new Animal(i, 1);
            rightcamp[i] = new Animal(i, 2);   //初始化动物对象
        }
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                preAnimalMap[i][j] = new ArrayList();   //初始化历史动物地图
            }
        }
        leftcamp[0] = new Animal(0, 0);
        rightcamp[0] = new Animal(0, 0);     //初始化两只空动物
        voidAni.setLocationC(-2);
        voidAni.setLocationR(-2);           //将空动物的位置移至地图外（不干扰地图内动物运动）
        nowCamp = 1;
        leftDead = 0;
        rightDead = 0;
        leftTrap = 0;
        rightTrap = 0;
        undoStep = 0;
        step = 0;
        isFirst = true;    //为阵营等棋局状态赋初值
    }                        //初始化动物对象以及历史动物地图

    public static void readMap() {
        String str;     //用于临时存储某一行的动物或是地形值
        try {
            Scanner inputTil = new Scanner(new File("tile.txt"));
            Scanner inputAni = new Scanner(new File("animal.txt"));     //创建两个Scanner，用于输入两个文件里的内容
            for (int i = 0; i <= 6; i++) {
                str = inputTil.nextLine();        //用str存储地形地图的某行
                for (int p = 0; p <= 8; p++) {
                    tileMap[i][p] = Integer.parseInt(str.substring(p, p + 1));    //将地形地图中的数字一个个存入tileMap数组中
                }
            }
            for (int j = 0; j <= 6; j++) {
                str = inputAni.nextLine();          //用str存储动物地图的某行
                for (int d = 0; d <= 8; d++) {
                    animalMap[j][d] = Integer.parseInt(str.substring(d, d + 1));     //将动物地图中的数字一个个存入animalMap数组中
                    if (d <= 2) {
                        alAnimalMap[j][d] = animalMap[j][d] + 10;            //判断动物是否在刚开始时处于左方。若是，则将动物值对应加10（即用十位代表动物的阵营，如右方象用28代表，左方鼠用11代表）
                    } else if (d >= 6) {
                        alAnimalMap[j][d] = animalMap[j][d] + 20;            //判断动物是否在刚开始时处于右方。若是，则将动物值对应加20
                    }
                    preAnimalMap[j][d].add(0, alAnimalMap[j][d]);        //在历史地图的第0步位置放置原初地图
                }
            }


        } catch (IOException e) {
            System.out.println("there's a wrong");
        }
    }                            //从文件里读取地图存入数组

    public static void putAnimal() {
        for (int j = 0; j <= 6; j++) {
            for (int d = 0; d <= 8; d++) {
                if (alAnimalMap[j][d] / 10 == 1) {
                    leftcamp[alAnimalMap[j][d] % 10].setLocationR(j);
                    leftcamp[alAnimalMap[j][d] % 10].setLocationC(d);
                    leftcamp[alAnimalMap[j][d] % 10].setAlive(true);
                } else if (alAnimalMap[j][d] / 10 == 2) {
                    rightcamp[alAnimalMap[j][d] % 10].setLocationR(j);
                    rightcamp[alAnimalMap[j][d] % 10].setLocationC(d);
                    rightcamp[alAnimalMap[j][d] % 10].setAlive(true);
                }
            }
        }
        leftcamp[0].setLocationC(-2);
        leftcamp[0].setLocationR(-2);
        rightcamp[0].setLocationC(-2);
        rightcamp[0].setLocationR(-2);

    }                          //更新动物，把动物对象的位置属性设置成动物地图上指示的对应位置值

    public static void initAnimal() {
        for (int j = 0; j <= 6; j++) {
            for (int d = 0; d <= 8; d++) {
                if (d <= 2) {
                    leftcamp[animalMap[j][d]].setLocationR(j);
                    leftcamp[animalMap[j][d]].setLocationC(d);
                } else if (d >= 6) {
                    rightcamp[animalMap[j][d]].setLocationR(j);
                    rightcamp[animalMap[j][d]].setLocationC(d);            //把动物放在地图上对应的位置，将它们的位置值设置为与地图上对应
                }
            }
        }
        leftcamp[0].setLocationC(-2);
        leftcamp[0].setLocationR(-2);
        rightcamp[0].setLocationC(-2);
        rightcamp[0].setLocationR(-2);          //将两只0动物放置在地图之外

    }                          //初始化动物位置

    public static void undo() {
        if (!isFirst) {
            undoStep++;
            preMap();
            putAnimal();
            if (nowCamp == 2) {
                nowCamp = 1;
            } else if (nowCamp == 1) {
                nowCamp = 2;
            }                               //交换阵营

        } else if (isFirst) {
            System.out.println("不能在开局悔棋");
        }
    }                                //悔棋方法

    public static void redo() {
        if (undoStep != 0) {
            undoStep--;
            preMap();
            putAnimal();
            if (nowCamp == 2) {
                nowCamp = 1;
            } else if (nowCamp == 1) {
                nowCamp = 2;
            }

        } else {
            System.out.println("可是你并没有悔棋(@_@;)");
        }
    }                                //撤销悔棋方法

    public static boolean camping(String str, Animal[] camp) {
        boolean isMove = false;
        if (str.length() == 2) {                                       //判断字符串长度是否为2
            int num = Integer.parseInt(str.substring(0, 1));
            String direction = str.substring(1, 2);
            if (camp[num].getAlive()) {                             //判断此动物的存活情况
                if (num != 1 && num != 6 && num != 7 && num != 0) {            //判断动物是否为狮虎或鼠
                    isMove = camp[num].move(direction);
                    if (isMove) {
                        step = step - undoStep;
                        undoStep = 0;
                        step++;
                    } else {
                        System.out.println("输入不可行");                   //若方向不是wasd则报错
                    }
                } else if (num == 1) {                                      //若为鼠
                    isMove = camp[num].ratMove(direction);
                    if (isMove) {
                        step = step - undoStep;
                        undoStep = 0;
                        isMove = true;
                        step++;
                    } else {
                        System.out.println("输入不可行");
                    }
                } else if (num == 6 || num == 7) {
                    isMove = camp[num].tiliMove(direction);
                    if (isMove) {
                        step = step - undoStep;
                        undoStep = 0;
                        isMove = true;
                        step++;
                    } else {
                        System.out.println("输入不可行");
                    }
                } else {
                    System.out.println("输入不可行");                         //数字输入有误则报错
                    isMove = false;
                }
            } else {
                System.out.println("请不要移动已被吃的獣");                     //若已死，则报错
                isMove = false;
            }
        } else {
            System.out.println("输入有误");                                   //若长度不为2，则报错
        }

        return isMove;                              //返回是否实质性移动
    }   //某方动物进行移动的方法

    public static Animal findAnimal(int lor, int loc) {
        for (int i = 0; i <= 8; i++) {
            if (leftcamp[i].getLocationC() == loc && leftcamp[i].getLocationR() == lor) {
                return leftcamp[i];
            } else if (rightcamp[i].getLocationC() == loc && rightcamp[i].getLocationR() == lor) {
                return rightcamp[i];
            }                                                   //匹配成功则返回对应动物对象
        }
        return voidAni;
    }          //在已知坐标的状况下返回那个坐标位置的动物对象

    public static void transPrint() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                if (animalMap[i][j] != 0) {                  //是否有动物
                    if (findAnimal(i, j).getCamp() == 1) {
                        printMap[i][j] = Integer.toString(animalMap[i][j]) + getAniCall(animalMap[i][j]) + " ";               //左阵营输出方法
                    } else if (findAnimal(i, j).getCamp() == 2) {
                        printMap[i][j] = " " + getAniCall(animalMap[i][j]) + Integer.toString(animalMap[i][j]);                 //右阵营输出方法
                    }
                } else {
                    printMap[i][j] = " " + getTilCall(tileMap[i][j]) + " ";                      //若无动物，输出地形
                }
            }
        }
    }                            //生成一张用于打印的地图

    public static String getAniCall(int value) {
        String name = "  ";
        switch (value) {
            case 1:
                name = "鼠";
                break;
            case 2:
                name = "猫";
                break;
            case 3:
                name = "狼";
                break;
            case 4:
                name = "狗";
                break;
            case 5:
                name = "豹";
                break;
            case 6:
                name = "虎";
                break;
            case 7:
                name = "狮";
                break;
            case 8:
                name = "象";
                break;
            default:
                name = "　";
                break;
        }
        return name;
    }                      //输入动物的值，返回动物的名字

    public static String getTilCall(int value) {
        String name = "  ";
        switch (value) {
            case 0:
                name="　";
                break;
            case 1:
                name = "水";
                break;
            case 2:
            case 4:
                name = "陷";
                break;
            case 3:
            case 5:
                name = "家";
                break;

            default:
                name = "错";
                break;
        }
        return name;
    }                       //输入地形的值，返回地形的名字

    public static void mapPrint() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 8; j++) {
                System.out.print(printMap[i][j]);
            }
            System.out.print("\n");
        }
    }                                //打印用于打印的地图

    public static void help() {
        try {
            Scanner help = new Scanner(new File("help.txt"));
            String use = "斗兽棋规则:\n" +
                    "斗兽棋的走法: 游戏开始时，红方先走，然后轮流走棋。每次可走动一只兽，每只兽每次走一方格，除己方兽穴和小河以外，前后左右均可。但是，狮、虎、鼠还有不同走法：\n" +
                    "狮虎跳河法：狮虎在小河边时，可以纵横对直跳过小河，且能把小河对岸的敌方较小的兽类吃掉，但是如果对方老鼠在河里，把跳的路线阻隔就不能跳，\n" +
                    "若对岸是对方比自己战斗力前的兽，也不可以跳过小河；\n" +
                    "鼠游过河法：鼠是唯一可以走入小河的兽，走法同陆地上一样，每次走一格，上下左右均可，而且，陆地上的其他兽不可以吃小河中的鼠，\n" +
                    "小河中的鼠也不能吃陆地上的象，鼠类互吃不受小河影响。\n" +
                    "\n" +
                    "斗兽棋的吃法: 斗兽棋吃法分普通吃法和特殊此法，普通吃法是按照兽的战斗力强弱，强者可以吃弱者。 特殊吃法如下：\n" +
                    "1、鼠吃象法：八兽的吃法除按照战斗力强弱次序外，惟鼠能吃象，象不能吃鼠。 \n" +
                    "2、互吃法：凡同类相遇，可互相吃。 \n" +
                    "3、陷阱：棋盘设陷阱，专为限制敌兽的战斗力（自己的兽，不受限制），敌兽走入陷阱，即失去战斗力，本方的任意兽类都可以吃去陷阱里的兽类。 \n" +
                    "综合普通吃法和特殊吃法，将斗兽棋此法总结如下：\n" +
                    "\n" +
                    "鼠可以吃象、鼠； \n" +
                    "猫可以吃猫、鼠； \n" +
                    "狼可以吃狼、猫、鼠； \n" +
                    "狗可以吃狗、狼、猫、鼠； \n" +
                    "豹可以吃豹、狗、狼、猫、鼠； \n" +
                    "虎可以吃虎、豹、狗、狼、猫、鼠； \n" +
                    "狮可以吃狮、虎、豹、狗、狼、猫、鼠； \n" +
                    "象可以吃象、狮、虎、豹、狗、狼、猫；\n" +
                    "\n" +
                    "我们使用数字1-8来表示动物，wasd代表上下左右。\n" +
                    "\n" +
                    "数字\t1\t2\t3\t4\t5\t6\t7\t8\n" +
                    "含义\t鼠\t猫\t狼\t狗\t豹\t虎\t狮\t象\n" +
                    "字母\tw\ta\ts\td\n" +
                    "含义\t向上\t向左\t向下\t向右\n" +
                    "\n\t输入 restart 重新开始游戏\n" +
                    "\t输入 help 查看帮助\n" +
                    "\t输入 undo 悔棋\n" +
                    "\t输入 redo 取消悔棋\n" +
                    "\t输入 exit 退出游戏\n";

            System.out.println(use);


        } catch (Exception e) {
            System.out.print("wrong");
        }
    }                                     //打印帮助


}
