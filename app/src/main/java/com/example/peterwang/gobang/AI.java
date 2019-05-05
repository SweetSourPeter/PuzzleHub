package com.example.peterwang.gobang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI implements Runnable {
    private int[][] chessArray;
    private int aiChess = gobangPanelAI.BLACK_CHESS;
    private List<Point> pointList;
    private AICallBack callBack;
    private int panelLength;


    private final static int FIVE = 10000;
    private final static int LIVE_FOUR = 4500;
    private final static int DEAD_FOUR = 2000;
    private final static int LIVE_THREE = 900;
    private final static int DEAD_THREE = 400;
    private final static int LIVE_TWO = 150;
    private final static int DEAD_TWO = 70;
    private final static int LIVE_ONE = 30;
    private final static int DEAD_ONE = 15;
    private final static int DEAD = 1;

    public AI(int[][] chessArray, AICallBack callBack) {
        pointList = new ArrayList<>();
        this.chessArray = chessArray;
        this.callBack = callBack;
        this.panelLength = chessArray.length;
    }

    public void aiBout() {
        new Thread(this).start();
    }

    private void checkPriority(Point p) {
        int aiPriority = checkSelf(p.getX(), p.getY());
        int userPriority = checkUser(p.getX(), p.getY());
        p.setPriority(aiPriority >= userPriority ? aiPriority : userPriority);
    }

    private int checkSelf(int x, int y) {
        return getHorizontalPriority(x, y, aiChess)
                + getVerticalPriority(x, y, aiChess)
                + getLeftSlashPriority(x, y, aiChess)
                + getRightSlashPriority(x, y, aiChess);
    }

    private int checkUser(int x, int y) {
        int userChess;
        if (aiChess == gobangPanelAI.WHITE_CHESS) {
            userChess = gobangPanelAI.BLACK_CHESS;
        } else {
            userChess = gobangPanelAI.WHITE_CHESS;
        }
        return getHorizontalPriority(x, y, userChess)
                + getVerticalPriority(x, y, userChess)
                + getLeftSlashPriority(x, y, userChess)
                + getRightSlashPriority(x, y, userChess);
    }

    @Override
    public void run() {
        pointList.clear();
        int blankCount = 0;
        for (int i = 0; i < panelLength; i++)
            for (int j = 0; j < panelLength; j++) {
                if (chessArray[i][j] == gobangPanelAI.NO_CHESS) {
                    Point p = new Point(i, j);
                    checkPriority(p);
                    pointList.add(p);
                    blankCount++;
                }
            }
        Point max = pointList.get(0);
        for (Point point : pointList) {
            if (max.getPriority() < point.getPriority()) {
                max = point;
            }
        }

        if (blankCount >= panelLength * panelLength - 1) {
            max = getStartPoint();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chessArray[max.getX()][max.getY()] = aiChess;
        callBack.aiAtTheBell();
    }

    public void setAiChess(int aiChess) {
        this.aiChess = aiChess;
    }


    private Point getStartPoint() {

        boolean isUse = true;

        Random random = new Random();
        int x = random.nextInt(5) + 5;
        int y = random.nextInt(5) + 5;

        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++) {
                if (chessArray[i][j] != gobangPanelAI.NO_CHESS) {
                    isUse = false;
                }
            }
        if (isUse) {
            return new Point(x, y);
        } else {
            return getStartPoint();
        }
    }



    private int getHorizontalPriority(int x, int y, int chess) {

        int connectCount = 1;

        boolean isStartStem = false;

        boolean isEndStem = false;


        if (y == 0) {
            isStartStem = true;
        } else {

            for (int i = y - 1; i >= 0; i--) {
                if (chessArray[x][i] != chess) {
                    isStartStem = chessArray[x][i] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == 0) {
                        isStartStem = true;
                    }
                }
            }
        }


        if (y == panelLength - 1) {
            isEndStem = true;
        } else {
            for (int i = y + 1; i < panelLength; i++) {
                if (chessArray[x][i] != chess) {
                    isEndStem = chessArray[x][i] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == panelLength - 1) {
                        isEndStem = true;
                    }
                }
            }
        }

        return calcPriority(connectCount, isStartStem, isEndStem);
    }

    private int getVerticalPriority(int x, int y, int chess) {

        int connectCount = 1;
        boolean isStartStem = false;
        boolean isEndStem = false;


        if (x == 0) {
            isStartStem = true;
        } else {
            for (int i = x - 1; i >= 0; i--) {
                if (chessArray[i][y] != chess) {
                    isStartStem = chessArray[i][y] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == 0) {
                        isStartStem = true;
                    }
                }
            }
        }


        if (x == panelLength - 1) {
            isEndStem = true;
        } else {
            for (int i = x + 1; i < panelLength; i++) {
                if (chessArray[i][y] != chess) {
                    isEndStem = chessArray[i][y] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == panelLength - 1) {
                        isEndStem = true;
                    }
                }
            }
        }

        return calcPriority(connectCount, isStartStem, isEndStem);
    }


    private int getLeftSlashPriority(int x, int y, int chess) {

        int connectCount = 1;
        boolean isStartStem = false;
        boolean isEndStem = false;


        if (x == 0 || y == 0) {
            isStartStem = true;
        } else {
            for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                if (chessArray[i][j] != chess) {
                    isStartStem = chessArray[i][j] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == 0 || j == 0) {
                        isStartStem = true;
                    }
                }
            }
        }


        if (x == panelLength - 1 || y == panelLength - 1) {
            isEndStem = true;
        } else {
            for (int i = x + 1, j = y + 1; i < panelLength && j < panelLength; i++, j++) {
                if (chessArray[i][j] != chess) {
                    isEndStem = chessArray[i][j] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == panelLength - 1 || j == panelLength - 1) {
                        isEndStem = true;
                    }
                }
            }
        }

        return calcPriority(connectCount, isStartStem, isEndStem);
    }


    private int getRightSlashPriority(int x, int y, int chess) {

        int connectCount = 1;
        boolean isStartStem = false;
        boolean isEndStem = false;


        if (x == panelLength - 1 || y == 0) {
            isStartStem = true;
        } else {
            for (int i = x + 1, j = y - 1; i < panelLength && j >= 0; i++, j--) {
                if (chessArray[i][j] != chess) {
                    isStartStem = chessArray[i][j] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == panelLength - 1 || j == 0) {
                        isStartStem = true;
                    }
                }
            }
        }


        if (x == 0 || y == panelLength - 1) {
            isEndStem = true;
        } else {
            for (int i = x - 1, j = y + 1; i >= 0 && j < panelLength; i--, j++) {
                if (chessArray[i][j] != chess) {
                    isEndStem = chessArray[i][j] != gobangPanelAI.NO_CHESS;
                    break;
                } else {
                    connectCount++;
                    if (i == 0 || j == panelLength - 1) {
                        isEndStem = true;
                    }
                }
            }
        }

        return calcPriority(connectCount, isStartStem, isEndStem);
    }


    private int calcPriority(int connectCount, boolean isStartStem, boolean isEndStem) {

        int priority = 0;
        if (connectCount >= 5) {
            priority = FIVE;
        } else {
            if (isStartStem && isEndStem) {
                priority = DEAD;
            } else if (isStartStem == isEndStem) {
                if (connectCount == 4) {
                    priority = LIVE_FOUR;
                } else if (connectCount == 3) {
                    priority = LIVE_THREE;
                } else if (connectCount == 2) {
                    priority = LIVE_TWO;
                } else if (connectCount == 1) {
                    priority = LIVE_ONE;
                }
            } else {
                if (connectCount == 4) {
                    priority = DEAD_FOUR;
                } else if (connectCount == 3) {
                    priority = DEAD_THREE;
                } else if (connectCount == 2) {
                    priority = DEAD_TWO;
                } else if (connectCount == 1) {
                    priority = DEAD_ONE;
                }
            }
        }
        return priority;
    }

}
