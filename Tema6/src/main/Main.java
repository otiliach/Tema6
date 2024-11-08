package main;

import activitati.Curs;
import activitati.Material;
import activitati.Rush;
import activitati.Tema;
import basic.Explorer;
import basic.Persoana;
import basic.Trainer;
import exceptions.InvalidAgeException;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class Main {

    public static void main(String[] args) {
        Persoana e1 = new Explorer("Ion", "Elena", "Brasov", 23, false);
        Persoana e2 = new Explorer("Ursu", "Anca", "Brasov", 19, false);
        Persoana e3 = new Explorer("Vulpe", "Maria", "Bucuresti", 30, true);
        Persoana t1 = new Trainer("Antonache", "Laura", "Brasov", 27, true);
        Persoana t2 = new Trainer("Cantemir", "Ion", "Bucuresti", 29, true);
        ArrayList<Persoana> exploreri = new ArrayList<>();
        String numeCurs = "JAVA";
        String dificultateCurs = "mediu";
        int costCurs = 1000;

        exploreri.add(e1);
        exploreri.add(e2);
        exploreri.add(e3);
        exploreri.add(t2);

        System.out.println(e1);
        System.out.println("Exporer-ul are responsabilitatile: " + e1.getResponsabilitati());
        System.out.println(t1);
        System.out.println("Trainer-ul are respomsabilitatile: " + t1.getResponsabilitati());

        System.out.println(e1.obtineIdentificator());
        System.out.println(t1.obtineIdentificator());

        Curs curs = new Curs(numeCurs, dificultateCurs, t1, exploreri, costCurs);

        //folositi obtineIdentificator() pentru a vedea mai clar tipul persoanei
        //ex: explorer_matei_SANDU pays 1000 vs:	trainer_sorina_ION pays 0
        afisarePersoane(exploreri, curs);

        Rush rush1 = new Rush();
        Rush rush2 = new Rush();
        Tema tema1 = new Tema();
        Tema tema2 = new Tema();
        Material material1 = new Material();
        Material material2 = new Material();

        curs.getActivitati().put("Rush 1", rush1);
        curs.getActivitati().put("Rush 2", rush2);
        curs.getActivitati().put("Tipuri primitive", tema1);
        curs.getActivitati().put("HashMap", tema2);
        curs.getActivitati().put("Material tipuri de date", material1);
        curs.getActivitati().put("Matrial HashMap", material2);
        //TODO 23: Definiti cate doua activitati de fiecare tip Rush, Tema, Material si adaugati-le in linkedhashmap-ul cursului apoi afisati calendarul cursului

        for (String key : curs.getActivitati().keySet()) {
            System.out.println("Activitatea " + key + " este de tip " + curs.getActivitati().get(key).getClass() + " cu descrierea \"" +
                               curs.getActivitati().get(key).getDescriere() + "\" si durata estimata de " + curs.getActivitati().get(key).getDurata());
        }

        //TODO M10.1:parsați fișierul creat
        File file = new File("src\\persoane.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    System.out.println(line);
                    //TODO M10.2: pentru fiecare rând parsați stringul (split pe baza de ":" și apoi "," în modelul de mai sus al formatului fișierului)
                    String[] arrayOfString = line.split("[:, ]+");
                    for (String word : arrayOfString) {
                        System.out.println(word);
                    }
                    //TODO M10.3: pentru fiecare linie foloșiti valorile citite și creați câte o instanță de tip Explorer sau Trainer și adaugați-o în lista corespunzâtoare
                    if (arrayOfString[1].equals("Explorer")) {
                        Persoana explorer = new Explorer(arrayOfString[2], arrayOfString[3], arrayOfString[4], Integer.parseInt(arrayOfString[5]), Boolean.parseBoolean(arrayOfString[6]));
                        if (explorer.getVarsta()<16){
                            throw new InvalidAgeException("The age must be at least 16.");
                        }
                        exploreri.add(explorer);
                    } else if (arrayOfString[1].equals("Trainer")) {
                        Persoana trainer = new Trainer(arrayOfString[2], arrayOfString[3], arrayOfString[4], Integer.parseInt(arrayOfString[5]), Boolean.parseBoolean(arrayOfString[6]));
                        exploreri.add(trainer);
                    }
                } catch (PatternSyntaxException e) {
                    System.out.println("java.util.regex.PatternSyntaxException occurs: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("java.lang.NumberFormatException occurs: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("java.lang.ArrayIndexOutOfBounds Exception occurs: "+e.getMessage());
                } catch (InvalidAgeException e){
                    System.out.println("InvalidAgeException occurs: "+e.getMessage());
                }
            }
    //TODO M10.4: folosiți tratare de excepții pentru: deschidere/parsare fișier, lungimea array-ului de String-uri splited, transformarea din String în Integer a valorii pentru vârsta etc
        } catch (FileNotFoundException e) {
            System.out.println("java.io.FileNotFoundException occurs: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("java.io.IOException occurs: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Parent Exception occurs");
        }

        afisarePersoane(exploreri, curs);

    }
    //TODO M10.5: modificați mediul/fișierul pentru a întâlni acele excepții (exemplu, nu respectați formatul agreat, adăugați un rând incomplet, schimbați formatul vârstei -> eg: 45ani în loc de 45) și observați comportamentul programului.

    //TODO 1: Creati o noua clasa Explorer in pachetul basic

    //TODO 2: Creati o noua clasa Trainer in pachetul basic

    //TODO 3: Ambele clase trebuie sa extinda clasa Persoana

    //TODO 4: Rezolvati erorile din cele doua noi subclase generand constructorii ce apeleaza constructorul din Persoana si implementand metodele necesare
    //Pentru responsabilitatile Trainerului se va returna un mesaj de tipul: "Pregatirea materialelor, evaluarea temelor, crearea contextului de lucru, raspunderea la intrebari"
    //Pentru responsabilitatile Explorerului se va returna un mesaj de tipul: "Parcurgerea materialelor, rezolvarea temelor, participarea la activitati";

    //TODO 5: In metoda main din clasa Main, definiti si initializati doua variabile de tip Persoana - e1 folosind constructorul clasei Explorer si t1 folosind constructorul clasei Trainer.
    //Afisati cele doua variabile si responsabilitatile fiecaruia

    //TODO 6: Suprascrieti in cele doua clase noi metoda obtineIdentificator astfel incat aceasta sa afiseze inainte de identificatorul utilizatorului calitatea acestuia:
    //ex: explorer_alexandru_ANDREI vs trainer_ana_POPESCU

    //TODO 7: Afisati cele rezultatul obtinut prin rularea metodei obtineIdentificator pe fiecare dintre variabile

    //TODO 8: Creati un pachet nou numit "activitati"

    //TODO 9: Creati o noua interfata numita "Activitate"

    //TODO 10: Pentru aceasta interfata definiti metodele publice:
    //getDescriere ce returneaza String
    //getDurata ce returneaza String

    //TODO 11: Creati in pachetul activitati clasele numite Rush, Tema, Material, Curs care implementeaza interfata Activitate

    //TODO 12: Implementati metodele necesare in fiecare clasa astfel incat sa returneze o descriere si un timp estimat de parcurgere (in afara de clasa Curs ce va fi tratata separat)
    //ex: pentru rush Rush se vor returna - descriere: "Activitate de lucru colaborativ & Q&A"; durata - "2h"

    //TODO 13: Pentru clasa Curs definiti proprietatile privat: "nume" (tip String), "dificultate" (tip String), "trainer" (tip Persoana), "exploreri" (tip ArrayList<Persoana>), int cost

    //TODO 14: Definiti getters/setters pentru proprietatile clasei Curs

    //TODO 15: Generati un constructor pentru clasa Curs cu toti parametrii clasei

    //TODO 16: Modificati metoda suprascrisa din clasa Curs astfel incat sa returneze o descriere ce contine numele si dificultatea cursului

    //TODO 17: Modificati metoda suprascrisa din clasa Curs astfel incat sa returneze o durata in functie de dificultate
    //"usor"->"1 luna"; "mediu"->"3 luni"; "dificil"->"5 luni"; altfel -> "necunoscut";

    //TODO 18: In clasa curs vom adauga o proprietate privata de tip LinkedHashMap<String, Activitate> ce reprezinta materialele acelui curs (numele si o Activitate)
    //Folosim LinkedHashMap pentru capacitatea acestuia de a pastra ordinea elementelor adaugate

    //TODO 19: Instantiati HashMap-ul atunci cand este definit si creati getter/setter pentru acesta

    //TODO 20: In metoda main din clasa Main vom realiza tot ce avem nevoie pentru a crea o instanta de curs:
    //Pentru trainer vom folosi instanta t1;
    //Pentru lista de exploreri vom crea 2 noi instante de exploreri e2, e3, dar si un nou trainer t2 si un nou ArrayList in care vom adauga aceste instante (e1, e2, e3 si t2)
    //Pentru nume vom folosi "JAVA"
    //Pentru dificultate vom folosi "mediu"
    //Pentru cost vom estima "1000"
    //Se va crea o instainta de Curs folosind variabilele de mai sus

    //TODO 21: Consideram ca trainerii pot participa la alte cursuri gratuiit si pentru aceasta definim metoda getCost(Persoana p) ce overload-uieste getterul getCost() fara niciun parametru
    //In metoda getCost cu parametru persoana se verifica daca persoana are instanta de trainer si atunci se returneaza 0, altfel se returneaza costul cursului

    //TODO 22: In metoda main din clasa Main parcurgeti lista de exploreri ai cursului definit si afisati pentru fiecare costul
    //folositi obtineIdentificator() pentru a vedea mai clar tipul persoanei
    //ex: explorer_matei_SANDU pays 1000 vs:	trainer_sorina_ION pays 0

    //TODO 23: Definiti cate doua activitati de fiecare tip Rush, Tema, Material si adaugati-le in linkedhashmap-ul cursului apoi afisati calendarul cursului
    //pentru afisare puteti folosi codul de mai jos:
	/*
	for(String key:c.getActivitati().keySet()) {
		System.out.println("Activitatea " + key + " este de tip " + c.getActivitati().get(key).getClass() + " cu descrierea \"" + 
					c.getActivitati().get(key).getDescriere() + "\" si durata estimata de " + c.getActivitati().get(key).getDurata() );
	}
	*/

    static void afisarePersoane(ArrayList<Persoana> exploreri, Curs curs) {
        for (Persoana exploarator : exploreri) {
            System.out.println(exploarator.obtineIdentificator() + " pays " + curs.getCost(exploarator));
        }
    }
}
		
	

