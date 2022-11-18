import java.util.Scanner;

class Monster {
    public final int id;
    public final String name;
    protected int hp, attack, defense;
    int currentAttack, currentDefense;


    public Monster(int id, String name, int hp, int attack, int defense) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.currentAttack = this.attack = attack;
        this.currentDefense = this.defense = defense;
    }

    public Result attack(Monster target) {
        double random = Math.random();
        if (random < .1) {
            currentAttack = attack * 2;
        } else if (random < .3) {
            currentAttack = (int) (attack * 1.5);
        } else if (random < .6) {
            currentAttack = attack;
        } else if (random < .8) {
            currentAttack = (int) (attack * 0.5);
        } else {
            return new Result(Result.Status.FAIL, target, 0);
        }
        int damage = Math.max(0, currentAttack - target.currentDefense);
        target.hp -= damage;
        if (target.hp <= 0) {
            return new Result(Result.Status.WIN, target, damage);
        }
        return new Result(Result.Status.OK, target, damage);
    }

    public Result.Status defend() {
        double random = Math.random();
        if (random < .1) {
            currentDefense = defense * 4;
        } else if (random < .3) {
            currentDefense = defense * 2;
        } else if (random < .7) {
            currentDefense = (int) (defense * 1.5);
        } else {
            currentDefense = defense;
            return Result.Status.FAIL;
        }
        return Result.Status.OK;
    }

    public void reset() {
        currentAttack = attack;
        currentDefense = defense;
    }
}

class Result {
    enum Status {
        OK, FAIL, WIN,
    }

    Status status;
    String name;
    int hp, attack, defense, damage;

    public Result(Status status, Monster target, int damage) {
        this.status = status;
        this.name = target.name;
        this.hp = target.hp;
        this.attack = target.currentAttack;
        this.defense = target.currentDefense;
        this.damage = damage;
    }

    @Override
    public String toString() {
        return String.format("%s （失血量： %d 剩餘血量： %d 攻擊力： %d 防守力： %d）", name, damage, hp, attack, defense);
    }
}

class Actor {
    enum Status {
        OK, FAIL, NOT_FOUND,
    }

    public final String id, name;
    private final String password;
    protected Monster monster;

    public Actor(String name, String id, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.monster = new Monster(1, "Monster", 100, 10, 10);
    }

    public Status signIn(String id, String password) {
        if (!id.equals(this.id)) {
            return Status.NOT_FOUND;
        } else if (!password.equals(this.password)) {
            return Status.FAIL;
        }
        return Status.OK;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}

class Author {
    public static String getAuthor() {
        return "102214209 楊斯惟";
    }
}

public class go {
    private static boolean signIn(Scanner scanner, Actor actor) {
        for (int i = 0; i < 3; i++) {
            System.out.print("請輸入密碼：");
            String password = scanner.nextLine();
            switch (actor.signIn(actor.id, password)) {
                case OK -> {
                    System.out.println("登入成功");
                    return true;
                }
                case FAIL -> {
                    if (i < 2) {
                        System.out.println("密碼錯誤");
                    } else {
                        System.out.println("登入錯誤次數超過上限");
                    }
                }
                case NOT_FOUND -> System.out.println("帳號不存在");
            }
        }
        return false;
    }

    private static void nextRound(Scanner scanner, Actor actor, Monster enemy) {
        while (true) {
            System.out.print("請選擇行動（1. 攻擊 2. 防守 3. 查詢遊戲作者）：");
            String choice = scanner.nextLine();
            if (choice.equals("3")) {
                System.out.println(Author.getAuthor());
                continue;
            }
            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("輸入錯誤，請重新輸入");
                continue;
            }
            String pcChoice = Math.random() < .5 ? "1" : "2";
            if (choice.equals("2")) {
                actor.getMonster().defend();
            }
            if (pcChoice.equals("2")) {
                enemy.defend();
            }
            Result result = null;
            if (choice.equals("1")) {
                result = actor.getMonster().attack(enemy);
                switch (result.status) {
                    case OK -> System.out.println("攻擊成功");
                    case FAIL -> System.out.println("攻擊失敗");
                    case WIN -> System.out.println("你贏了");
                }
                if (!pcChoice.equals("1")) {
                    System.out.println(new Result(Result.Status.OK, actor.getMonster(), 0));
                    System.out.println(result);
                }
                if (result.status == Result.Status.WIN) {
                    break;
                }
            }
            if (pcChoice.equals("1")) {
                Result pcResult = enemy.attack(actor.getMonster());
                switch (pcResult.status) {
                    case OK -> System.out.println("電腦攻擊成功");
                    case FAIL -> System.out.println("電腦攻擊失敗");
                    case WIN -> System.out.println("你輸了");
                }
                if (result == null) {
                    result = new Result(Result.Status.OK, enemy, 0);
                }
                System.out.println(pcResult);
                System.out.println(result);
                if (result.status == Result.Status.WIN || pcResult.status == Result.Status.WIN) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Actor actor = new Actor("楊斯惟", "102214209", "209");
        if (!signIn(scanner, actor)) return;

        System.out.printf("歡迎光臨 %s%n", actor.name);

        actor.setMonster(new Monster(1, "怪獸", 100, 20, 10));
        Monster enemy = new Monster(2, "電腦", 100, 20, 10);

        while (actor.getMonster().hp > 0 && enemy.hp > 0) {
            actor.getMonster().reset();
            enemy.reset();
            nextRound(scanner, actor, enemy);
        }

        if (actor.getMonster().hp > 0) {
            System.out.println("恭喜你贏了");
        } else {
            System.out.println("你輸了");
        }

        System.out.println("遊戲結束");

        scanner.close();
    }
}