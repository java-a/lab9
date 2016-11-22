package controller;

import controller.history.GameHistory;
import entity.Board;
import entity.animal.Animal;
import exception.CannotRedoException;
import exception.CannotUndoException;
import exception.GameWinException;
import exception.InvalidActionException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lifengshuang on 9/22/16.
 */
public class GameController {

    private static Side turn = Side.LEFT;
    private static ArrayList<String> commands = new ArrayList<>();

    public static void startGame() {

        printWelcomeMessage();
        printHelpMessage();

        Scanner scanner = new Scanner(System.in);

        Pattern animalMovePattern = Pattern.compile("^([1-8])([wasd])$");

        Board.getInstance().printBoard();

        while (true) {

            if (turn == Side.LEFT) {
                System.out.print("左方玩家行动: ");
            } else {
                System.out.print("右方玩家行动: ");
            }
            String input = scanner.nextLine();
            Matcher matcher = animalMovePattern.matcher(input);
            if (input.equals("exit")) {
                break;
            } else if (input.equals("restart")) {
                turn = Side.LEFT;
                Board.getInstance().restart();
                Board.getInstance().printBoard();
            } else if (input.equals("help")) {
                printHelpMessage();
            } else if (input.equals("undo")) {
                try {
                    GameHistory.getInstance().undo();
                    nextTurn();
                    Board.getInstance().printBoard();
                } catch (CannotUndoException e) {
                    System.out.println("已经退回到开局,不能再悔棋了!");
                }
            } else if (input.equals("redo")) {
                try {
                    GameHistory.getInstance().redo();
                    nextTurn();
                    Board.getInstance().printBoard();
                } catch (CannotRedoException e) {
                    System.out.println("已经回到最后的记录,不能再取消悔棋了!");

                }
            } else if (matcher.find()) {

                int power = Integer.parseInt(matcher.group(1));
                Direction direction = Direction.parseDirection(matcher.group(2));

                try {

                    Animal animal = Board.getInstance().getAnimal(turn, power);

                    /**
                     * act是核心的动物移动/攻击方法，第二个参数表示动物是否真的要动，在无子可动的检查中，第二个参数是true
                     * act方法的定义在Animal类中，具体实现在AnimalCanJumpRiver, AnimalCanSwim 和 AnimalWithoutSkill 类中。
                     */
                    animal.act(direction, false);

                    nextTurn();
                    Board.getInstance().printBoard();

                } catch (InvalidActionException e) {
                    System.out.println(e.getMessage());
                } catch (GameWinException e) {
                    Board.getInstance().printBoard();
                    if (e.getSide() == Side.LEFT) {
                        System.out.println("左方玩家胜利:" + e.getMessage());
                    } else {
                        System.out.println("右方玩家胜利:" + e.getMessage());
                    }
                    System.out.println("输入 \"restart\" 重新开始");
                    while (!scanner.nextLine().equals("restart")) {
                        System.out.println("输入 \"restart\" 重新开始");
                    }
                    turn = Side.LEFT;
                    Board.getInstance().restart();
                    Board.getInstance().printBoard();
                }
            } else {
                System.out.println("不能识别指令\"" + input + "\", 请重新输入");
            }
        }
    }

    private static void nextTurn() {
        if (turn == Side.LEFT) {
            turn = Side.RIGHT;
        } else {
            turn = Side.LEFT;
        }
    }

    private static void printWelcomeMessage() {
        String welcome = "斗兽棋游戏\n";
        System.out.println(welcome);
    }

    private static void printHelpMessage() {
        String help = "指令介绍:\n" +
                "\n" +
                "1. 移动指令\n" +
                "\t移动指令由两个部分组成。\n" +
                "\t第一个部分是数字1-8,根据战斗力分别对应鼠(1),猫(2),狼(3),狗(4),豹(5),虎(6),狮(7),象(8)\n" +
                "\t第二个部分是字母wasd中的一个,w对应上方向,a对应左方向,s对应下方向,d对应右方向\n" +
                "\t比如指令 \"1d\" 表示鼠向右走, \"4w\" 表示狗向左走\n" +
                "\n" +
                "2. 游戏指令\n" +
                "\t输入 restart 重新开始游戏\n" +
                "\t输入 help 查看帮助\n" +
                "\t输入 undo 悔棋\n" +
                "\t输入 redo 取消悔棋\n" +
                "\t输入 exit 退出游戏\n" +
                "\n";
        System.out.println(help);
    }

    public enum Side {
        LEFT, RIGHT
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction parseDirection(String direction) {
            switch (direction) {
                case "w":
                    return UP;
                case "s":
                    return DOWN;
                case "a":
                    return LEFT;
                case "d":
                    return RIGHT;
            }
            return null;
        }
    }
}
