package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Hallgato> hallgatok = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Példányosított szereplők
        hallgatok.add(new Hallgato("Végh Csaba", "ASZ694", true));
        hallgatok.add(new Hallgato("Kiss Anna", "BDF123", true));

        Oktato oktato = new Oktato("Dr. Nagy Éva", "XYZ789", "Informatika");
        Adminisztrator admin = new Adminisztrator("Tóth Anna", "ADM456");

        while (true) {
            System.out.println("\n--- Neptun rendszer ---");
            System.out.println("1. Hallgatói bejelentkezés");
            System.out.println("2. Oktatói bejelentkezés");
            System.out.println("3. Adminisztrátori bejelentkezés");
            System.out.println("0. Kilépés");
            System.out.print("Válasszon: ");
            String valasztas = scanner.nextLine();

            switch (valasztas) {
                case "1":
                    hallgatoiMenu();
                    break;
                case "2":
                    oktatoiMenu(oktato);
                    break;
                case "3":
                    adminisztratoriMenu(admin);
                    break;
                case "0":
                    System.out.println("Kilépés...");
                    return;
                default:
                    System.out.println("Érvénytelen választás.");
            }
        }
    }

    static void hallgatoiMenu() {
        System.out.print("Neptun-kód: ");
        String kod = scanner.nextLine();
        Hallgato h = keresHallgatot(kod);
        if (h == null) {
            System.out.println("Hallgató nem található.");
            return;
        }

        System.out.println("Üdv, " + h.getNev() + "!");
        while (true) {
            System.out.println("\n--- Hallgatói menü ---");
            System.out.println("1. Vizsgára jelentkezés");
            System.out.println("2. Státusz megtekintése");
            System.out.println("0. Kilépés");
            System.out.print("Választás: ");
            String val = scanner.nextLine();
            switch (val) {
                case "1":
                    System.out.print("Tantárgy neve: ");
                    String tantargy = scanner.nextLine();
                    h.vizsgaraJelentkezes(tantargy);
                    break;
                case "2":
                    System.out.println("Státusz: " + (h.isAktiv() ? "aktív" : "passzív"));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Érvénytelen opció.");
            }
        }
    }

    static void oktatoiMenu(Oktato oktato) {
        System.out.println("Üdv, " + oktato.getNev() + " (Oktató)!");
        while (true) {
            System.out.println("\n--- Oktatói menü ---");
            System.out.println("1. Hallgatók listázása");
            System.out.println("2. Jegy adása hallgatónak");
            System.out.println("0. Kilépés");
            System.out.print("Választás: ");
            String val = scanner.nextLine();
            switch (val) {
                case "1":
                    listazHallgatok();
                    break;
                case "2":
                    System.out.print("Hallgató Neptun-kódja: ");
                    String kod = scanner.nextLine();
                    Hallgato h = keresHallgatot(kod);
                    if (h == null) {
                        System.out.println("Nem található.");
                        break;
                    }
                    System.out.print("Tantárgy neve: ");
                    String targy = scanner.nextLine();
                    System.out.print("Jegy (1-5): ");
                    int jegy = Integer.parseInt(scanner.nextLine());
                    oktato.jegyetAd(h, targy, jegy);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Érvénytelen opció.");
            }
        }
    }

    static void adminisztratoriMenu(Adminisztrator admin) {
        System.out.print("Admin azonosító (ADM456): ");
        String kod = scanner.nextLine();
        if (!admin.getNeptunKod().equalsIgnoreCase(kod)) {
            System.out.println("Hibás admin belépés.");
            return;
        }

        System.out.println("Üdv, " + admin.getNev() + " (Adminisztrátor)!");
        while (true) {
            System.out.println("\n--- Admin menü ---");
            System.out.println("1. Hallgatók listázása");
            System.out.println("2. Hallgató felvétele");
            System.out.println("3. Hallgató törlése");
            System.out.println("4. Státusz módosítása");
            System.out.println("0. Kilépés");
            System.out.print("Választás: ");
            String val = scanner.nextLine();

            switch (val) {
                case "1":
                    listazHallgatok();
                    break;
                case "2":
                    System.out.print("Hallgató neve: ");
                    String nev = scanner.nextLine();
                    System.out.print("Neptun-kód: ");
                    String neptun = scanner.nextLine();
                    hallgatok.add(new Hallgato(nev, neptun, true));
                    System.out.println("Hallgató felvéve.");
                    break;
                case "3":
                    System.out.print("Neptun-kód: ");
                    String torlendo = scanner.nextLine();
                    Hallgato torles = keresHallgatot(torlendo);
                    if (torles != null) {
                        hallgatok.remove(torles);
                        System.out.println("Hallgató törölve.");
                    } else {
                        System.out.println("Nem található.");
                    }
                    break;
                case "4":
                    System.out.print("Neptun-kód: ");
                    String modosit = scanner.nextLine();
                    Hallgato mod = keresHallgatot(modosit);
                    if (mod != null) {
                        System.out.print("Új státusz (true/false): ");
                        boolean uj = Boolean.parseBoolean(scanner.nextLine());
                        admin.statuszModositas(mod, uj);
                    } else {
                        System.out.println("Nem található.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Érvénytelen opció.");
            }
        }
    }

    static Hallgato keresHallgatot(String kod) {
        for (Hallgato h : hallgatok) {
            if (h.getNeptunKod().equalsIgnoreCase(kod)) {
                return h;
            }
        }
        return null;
    }

    static void listazHallgatok() {
        if (hallgatok.isEmpty()) {
            System.out.println("Nincs hallgató a rendszerben.");
        } else {
            for (Hallgato h : hallgatok) {
                System.out.println(h.getNev() + " (" + h.getNeptunKod() + ") - " + (h.isAktiv() ? "aktív" : "passzív"));
            }
        }
    }
}



class Szemely {
    protected String nev;
    protected String neptunKod;

    public Szemely(String nev, String neptunKod) {
        this.nev = nev;
        this.neptunKod = neptunKod;
    }

    public String getNev() {
        return nev;
    }

    public String getNeptunKod() {
        return neptunKod;
    }
}

class Hallgato extends Szemely {
    private boolean aktiv;

    public Hallgato(String nev, String neptunKod, boolean aktiv) {
        super(nev, neptunKod);
        this.aktiv = aktiv;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public void vizsgaraJelentkezes(String tantargy) {
        System.out.println(nev + " jelentkezett a(z) " + tantargy + " vizsgára.");
    }
}

class Oktato extends Szemely {
    private String tanszek;

    public Oktato(String nev, String neptunKod, String tanszek) {
        super(nev, neptunKod);
        this.tanszek = tanszek;
    }

    public void jegyetAd(Hallgato hallgato, String tantargy, int jegy) {
        System.out.println(hallgato.getNev() + " kapott egy " + jegy + "-öst a(z) " + tantargy + " tantárgyból.");
    }
}

class Adminisztrator extends Szemely {
    public Adminisztrator(String nev, String neptunKod) {
        super(nev, neptunKod);
    }

    public void statuszModositas(Hallgato hallgato, boolean ujStatusz) {
        hallgato.setAktiv(ujStatusz);
        System.out.println(hallgato.getNev() + " státusza módosítva: " + (ujStatusz ? "aktív" : "passzív"));
    }
}
