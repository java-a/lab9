/**
 * Created by qyy on 2016/9/29.
 */
public class Animal {
    private int value;                                   //存储动物的值
    private int locationR;                               //存储动物所在的行数
    private int locationC;                                //存储动物所在的列数
    private boolean isAlive = true;                       //存储动物的存活情况
    private int camp;                                     //存储动物的阵营
    private boolean isWater = false;                      //存储动物的在水情况

    public Animal(int val, int cam) {
        value = val;
        camp = cam;
    }                      //构造函数

    public int getValue() {
        return value;
    }                      //获取动物对象的值

    public int getLocationR() {
        return locationR;
    }                  //获取动物对象所在行数

    public int getLocationC() {
        return locationC;
    }                   //获取动物对象所在列数

    public boolean getAlive() {
        return isAlive;
    }                     //获取动物的存活情况

    public int getCamp() {
        return camp;
    }                              //获取动物的阵营

    public void setAlive(boolean alive) {
        isAlive = alive;
    }               //设定动物的存活情况

    public void setLocationR(int lor) {
        locationR = lor;
    }                 //设定动物所在的行数

    public void setLocationC(int loc) {
        locationC = loc;
    }                   //设定动物所在的列数

    public boolean move(String direction) {
        switch (direction) {
            case "w":
                return fight(locationR - 1, locationC);
            case "a":
                return fight(locationR, locationC - 1);
            case "s":
                return fight(locationR + 1, locationC);
            case "d":
                return fight(locationR, locationC + 1);
            default:
                return false;                  //方向命令未输入wasd，返回的isMove为false
        }
    }                              //普通动物的移动方法

    public boolean ratMove(String direction) {
        switch (direction) {
            case "w":
                return ratFight(locationR - 1, locationC);
            case "a":
                return ratFight(locationR, locationC - 1);
            case "s":
                return ratFight(locationR + 1, locationC);
            case "d":
                return ratFight(locationR, locationC + 1);
            default:
                return false;                 //方向命令未输入wasd，返回的isMove为false
        }
    }                           //老鼠的移动方法

    public boolean tiliMove(String direction) {
        switch (direction) {
            case "w":
                return tiliFight(locationR - 1, locationC, locationR - 3, locationC);
            case "a":
                return tiliFight(locationR, locationC - 1, locationR, locationC - 4);
            case "s":
                return tiliFight(locationR + 1, locationC, locationR + 3, locationC);
            case "d":
                return tiliFight(locationR, locationC + 1, locationR, locationC + 4);
            default:
                return false;                              //方向命令未输入wasd，返回的isMove为false
        }
    }                            //老虎狮子的移动方法

    public boolean subMovable(int r, int c) {
        /* r,c两个参数意味着动物即将到达的位置，即一个行走趋向。
          如向上走一格这一行为中，r=locationR-1，c=locationC */

        if ((r < 0 || r > 6) || (c < 0 || c > 8)) {                 //若动物位置将走向边界外
            return false;
        } else if (Jungle.findAnimal(r, c).getValue() != 0) {                  //若不为空动物
            if (Jungle.findAnimal(r, c).getCamp() == camp) {                   //若为己方动物
                return false;
            } else if (Jungle.findAnimal(r, c).getValue() > value || (Jungle.findAnimal(r, c).getValue() == 1 && value == 8)) {           //若为敌方较强动物
                return false;
            } else if (Jungle.tileMap[r][c] == 1 || (Jungle.tileMap[r][c] == camp * 2 + 1)) {                               //若为水或者己方巢穴
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }                              //判断普通动物某个方向是否能动

    public boolean subRatMovable(int r, int c) {
        if ((r < 0 || r > 6) || (c < 0 || c > 8)) {
            return false;
        } else if (Jungle.findAnimal(r, c).getValue() != 0) {
            if (Jungle.findAnimal(r, c).getCamp() == camp) {
                return false;
            } else if (Jungle.findAnimal(r, c).getValue() > value || (Jungle.findAnimal(r, c).getValue() == 1 && value == 8)) {
                return false;
            } else if (Jungle.tileMap[r][c] == camp * 2 + 1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }                           //判断老鼠某个方向是否能动

    public boolean subTiliMovable(int r, int c, int r2, int c2) {

        if ((r < 0 || r > 6) || (c < 0 || c > 8)) {
            return false;
        } else if (Jungle.tileMap[r][c] != 1) {
            if (Jungle.findAnimal(r, c).getValue() != 0) {
                if (Jungle.findAnimal(r, c).getCamp() == camp) {
                    return false;
                } else if (Jungle.findAnimal(r, c).getValue() > value || (Jungle.findAnimal(r, c).getValue() == 1 && value == 8)) {
                    return false;
                } else if (Jungle.tileMap[r][c] == 1 || (Jungle.tileMap[r][c] == camp * 2 + 1)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {                                            //如果前方要跳河
            boolean hasRat = false;                       //判断水路上是否有敌方老鼠
            if (r2 < locationR) {
                for (int i = locationR; i >= r2; i--) {
                    if (Jungle.tileMap[i][locationC] == 1 && Jungle.findAnimal(i, locationC).getValue() == 1 && Jungle.findAnimal(i, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (c2 < locationC) {
                for (int j = locationC; j >= c2; j--) {
                    if (Jungle.tileMap[locationR][j] == 1 && Jungle.findAnimal(locationR, j).getValue() == 1 && Jungle.findAnimal(j, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (r2 > locationR) {
                for (int i = locationR; i <= r2; i++) {
                    if (Jungle.tileMap[i][locationC] == 1 && Jungle.findAnimal(i, locationC).getValue() == 1 && Jungle.findAnimal(i, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (c2 > locationC) {
                for (int j = locationC; j <= c2; j++) {
                    if (Jungle.tileMap[locationR][j] == 1 && Jungle.findAnimal(locationR, j).getValue() == 1 && Jungle.findAnimal(j, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            }
            if (!hasRat) {
                if ((r2 < 0 || r2 > 6) || (c2 < 0 || c2 > 8)) {
                    return false;
                } else if (Jungle.findAnimal(r2, c2).getValue() != 0) {
                    if (Jungle.findAnimal(r2, c2).getCamp() == camp) {
                        return false;
                    } else if (Jungle.findAnimal(r2, c2).getValue() > value || (Jungle.findAnimal(r2, c2).getValue() == 1 && value == 8)) {
                        return false;
                    } else if (Jungle.tileMap[r2][c2] == camp * 2 + 1) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }

        }
    }            //判断狮虎某个方向是否能动

    public boolean isMovable() {
        int side = 0;           //记录可动的方向数
        if (subMovable(locationR - 1, locationC)) {
            side++;
        }
        if (subMovable(locationR + 1, locationC)) {
            side++;
        }
        if (subMovable(locationR, locationC - 1)) {
            side++;
        }
        if (subMovable(locationR, locationC + 1)) {
            side++;
        }
        if (side == 0) {          //如果没有可动的方向
            return false;
        } else {
            return true;
        }
    }                                             //判断普通动物是否能动

    public boolean isRatMovable() {
        int side = 0;
        if (subRatMovable(locationR - 1, locationC)) {
            side++;
        }
        if (subRatMovable(locationR + 1, locationC)) {
            side++;
        }
        if (subRatMovable(locationR, locationC - 1)) {
            side++;
        }
        if (subRatMovable(locationR, locationC + 1)) {
            side++;
        }
        if (side == 0) {
            return false;
        } else {
            return true;                   //同isMovable方法
        }
    }                                          //判断老鼠是否能动

    public boolean isTiliMovable() {
        int side = 0;
        if (subTiliMovable(locationR - 1, locationC, locationR - 3, locationC)) {
            side++;
        }
        if (subTiliMovable(locationR + 1, locationC, locationR + 3, locationC)) {
            side++;
        }
        if (subTiliMovable(locationR, locationC - 1, locationR, locationC - 4)) {
            side++;
        }
        if (subTiliMovable(locationR, locationC + 1, locationR, locationC + 4)) {
            side++;
        }
        if (side == 0) {
            return false;
        } else {
            return true;
        }                                       //同isMovable方法
    }                                          //判断狮虎是否能动

    public boolean fight(int r, int c) {
        if ((r >= 0 && r <= 6 && c >= 0 && c <= 8) && (Jungle.tileMap[r][c] != 1) && (Jungle.tileMap[r][c] != camp * 2 + 1)) {           //若在棋盘内，且不会走进水中和自家巢穴中
            if (Jungle.findAnimal(r, c).getValue() == 0) {                           //若前进方向无动物
                Jungle.isFirst = false;
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;//交换阵营
            } else if (Jungle.findAnimal(r, c).getCamp() == camp) {                    //若前进方向为友方
                System.out.println("不能和友方单位重叠");
                return false;
            } else if ((Jungle.findAnimal(r, c).getValue() <= value && (value != 8 || Jungle.findAnimal(r, c).getValue() != 1)) || (Jungle.findAnimal(r, c).getValue() == 8 && value == 1) ||
                    (Jungle.tileMap[r][c] == 2 && Jungle.findAnimal(r, c).getCamp() == 2) || (Jungle.tileMap[r][c] == 4 && Jungle.findAnimal(r, c).getCamp() == 1)) {   //若对方可被吃
                Jungle.isFirst = false;
                Jungle.findAnimal(r, c).setAlive(false);        //使对方的存活状态设定为false
                Jungle.findAnimal(r, c).setLocationR(-3);
                Jungle.findAnimal(-1, c).setLocationC(-3);      //将对方移出地图
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;
            } else {
                System.out.println(Jungle.getAniCall(value) + "不能吃" + Jungle.getAniCall(Jungle.findAnimal(r, c).getValue()));
                return false;
            }
        } else {
            System.out.println("对不起，已无法移动");
            return false;
        }
    }                                        //普通动物行走以及与其他动物对抗

    public boolean ratFight(int r, int c) {
        if ((r >= 0 && r <= 6 && c >= 0 && c <= 8) && (Jungle.tileMap[r][c] != camp * 2 + 1)) {
            if (Jungle.findAnimal(r, c).getValue() == 0) {
                if (Jungle.tileMap[r][c] == 1) {
                    isWater = true;
                } else if (Jungle.tileMap[r][c] != 1) {
                    isWater = false;
                }
                Jungle.isFirst = false;
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;

            } else if (Jungle.findAnimal(r, c).getCamp() == camp) {
                System.out.println("不能和友方单位重叠");
                return false;
            } else if (((Jungle.findAnimal(r, c).getValue() <= value || (Jungle.findAnimal(r, c).getValue() == 8 && value == 1)) && (!isWater || Jungle.findAnimal(r, c).getValue() == 1)) || (Jungle.tileMap[r][c] == 2 && Jungle.findAnimal(r, c).getCamp() == 2) || (Jungle.tileMap[r][c] == 4 && Jungle.findAnimal(r, c).getCamp() == 1)) {
                Jungle.isFirst = false;
                Jungle.findAnimal(r, c).setAlive(false);
                Jungle.findAnimal(r, c).setLocationR(-3);
                Jungle.findAnimal(-1, c).setLocationC(-3);
                if (Jungle.tileMap[r][c] == 1) {
                    isWater = true;
                } else if (Jungle.tileMap[r][c] != 1) {
                    isWater = false;
                }
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;
            } else if (Jungle.findAnimal(r, c).getValue() == 8 && value == 1) {
                System.out.println("鼠不能在水中吃象");
                return false;
            } else if (Jungle.findAnimal(r, c).getValue() > value) {
                System.out.println(Jungle.getAniCall(value) + "不能吃" + Jungle.getAniCall(Jungle.findAnimal(r, c).getValue()));
                return false;

            } else if (Jungle.findAnimal(r, c).getValue() != 1) {
                System.out.println("鼠不可在水中吃獣");
                return false;
            }


        } else {
            System.out.println("对不起，已无法移动");
            return false;
        }
        return false;
    }                                       //老鼠行走以及与其他动物对抗

    public boolean tiliFight(int r, int c, int r2, int c2) {
        if ((r >= 0 && r <= 6 && c >= 0 && c <= 8) && (Jungle.tileMap[r][c] != 1) && (Jungle.tileMap[r][c] != camp * 2 + 1)) {
            if (Jungle.findAnimal(r, c).getValue() == 0) {
                Jungle.isFirst = false;
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;

            } else if (Jungle.findAnimal(r, c).getCamp() == camp) {
                System.out.println("不能和友方单位重叠");
                return false;
            } else if ((Jungle.findAnimal(r, c).getValue() <= value || (Jungle.findAnimal(r, c).getValue() == 8 && value == 1)) || (Jungle.tileMap[r][c] == 2 && Jungle.findAnimal(r, c).getCamp() == 2) || (Jungle.tileMap[r][c] == 4 && Jungle.findAnimal(r, c).getCamp() == 1)) {
                Jungle.isFirst = false;
                Jungle.findAnimal(r, c).setAlive(false);
                Jungle.findAnimal(r, c).setLocationR(-3);
                Jungle.findAnimal(-1, c).setLocationC(-3);
                locationR = r;
                locationC = c;
                if (Jungle.nowCamp == 2) {
                    Jungle.nowCamp = 1;
                } else if (Jungle.nowCamp == 1) {
                    Jungle.nowCamp = 2;
                }
                return true;
            } else {
                System.out.println(Jungle.getAniCall(value) + "不能吃" + Jungle.getAniCall(Jungle.findAnimal(r, c).getValue()));
                return false;
            }
        } else if ((r >= 0 && r <= 6 && c >= 0 && c <= 8) && (Jungle.tileMap[r][c] == 1)) {
            boolean hasRat = false;
            if (r2 < locationR) {
                for (int i = locationR; i >= r2; i--) {
                    if (Jungle.tileMap[i][locationC] == 1 && Jungle.findAnimal(i, locationC).getValue() == 1 && Jungle.findAnimal(i, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (c2 < locationC) {
                for (int j = locationC; j >= c2; j--) {
                    if (Jungle.tileMap[locationR][j] == 1 && Jungle.findAnimal(locationR, j).getValue() == 1 && Jungle.findAnimal(locationR, j).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (r2 > locationR) {
                for (int i = locationR; i <= r2; i++) {
                    if (Jungle.tileMap[i][locationC] == 1 && Jungle.findAnimal(i, locationC).getValue() == 1 && Jungle.findAnimal(i, locationC).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            } else if (c2 > locationC) {
                for (int j = locationC; j <= c2; j++) {
                    if (Jungle.tileMap[locationR][j] == 1 && Jungle.findAnimal(locationR, j).getValue() == 1 && Jungle.findAnimal(locationR, j).getCamp() != camp) {
                        hasRat = true;
                    }
                }
            }
            if (!hasRat) {
                if (Jungle.findAnimal(r2, c2).getValue() == 0) {
                    Jungle.isFirst = false;
                    locationR = r2;
                    locationC = c2;
                    if (Jungle.nowCamp == 2) {
                        Jungle.nowCamp = 1;
                    } else if (Jungle.nowCamp == 1) {
                        Jungle.nowCamp = 2;
                    }
                    return true;
                } else if (Jungle.findAnimal(r2, c2).getCamp() == camp) {
                    System.out.println("不能和友方单位重叠");
                    return false;
                } else if (Jungle.findAnimal(r2, c2).getValue() <= value || (Jungle.findAnimal(r2, c2).getValue() == 8 && value == 1)) {
                    Jungle.isFirst = false;
                    Jungle.findAnimal(r2, c2).setAlive(false);
                    Jungle.findAnimal(r2, c2).setLocationR(-3);
                    Jungle.findAnimal(-1, c2).setLocationC(-3);
                    locationR = r2;
                    locationC = c2;
                    if (Jungle.nowCamp == 2) {
                        Jungle.nowCamp = 1;
                    } else if (Jungle.nowCamp == 1) {
                        Jungle.nowCamp = 2;
                    }
                    return true;
                } else {
                    System.out.println(Jungle.getAniCall(value) + "不能吃" + Jungle.getAniCall(Jungle.findAnimal(r2, c2).getValue()));
                    return false;
                }
            } else {
                System.out.println("对不起，水路上有老鼠");
                return false;
            }
        } else {
            System.out.println("对不起，已无法移动");
            return false;
        }
    }                     //狮虎行走以及与其他动物对抗


}
