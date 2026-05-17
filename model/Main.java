package model;


import enums.*;
import exceptions.*;
import model.researcher.*;
import model.teacher.*;
import exceptions.MaxFailReachedException;
import java.time.LocalDate;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static University uni = University.getInstance("KBTU University");

    static Admin admin;
    static Manager manager;
    static Teacher profCool;      
    static Teacher lectorAURA;   
    static Student togzhan, moli, dilara, ersultan;
    static ResearcherEmployee researcherwork; 
    static Course  bitCourse, oopCourse, dbCourse;
    static ResearchProject Project;

    public static void main(String[] args) {
        System.out.println("LOGIN");
        System.out.print("Email: ");
        String em = sc.nextLine();

        System.out.print("Password: ");
        String pw = sc.nextLine();

        try {
            admin.login(em, pw);
        } catch (InfoNotCorrectException e) {
        System.out.println("Access denied: " + e.getMessage());
        return;
    }
        seedData();

        boolean running = true;
        while (running) {
            printMainMenu();
            switch (readInt()) {
                case 1  -> adminMenu();
                case 2  -> managerMenu();
                case 3  -> teacherMenu();
                case 4  -> studentMenu();
                case 5  -> researchMenu();
                case 6  -> reportMenu();
                case 0  -> { System.out.println("Goodbye!"); running = false; }
                default -> System.out.println("  Invalid option.");
            }
        }
        sc.close();
    }


    static void seedData() {
        admin = new Admin(0, "AAARH", "Admin", "admin@uni.kz", "admin123");

        manager = new Manager(3, "Kira", "Light", "kira@uni.kz", "pass123","kira_manager","Academic Office", 400000.0, ManagerType.OR);

        profCool   = new Teacher(1, "Zuko", "Flame",  Title.PROFESSOR, "Fire@uni.kz", "pass123");
        lectorAURA = new Teacher(2, "Life", "Good",     Title.LECTOR,    "good@uni.kz",   "pass123");

        togzhan = new Student(4, "togzann", "ree", "to@uni.kz", "pass123", (int) 4.0, "CS");
        moli  = new Student(5, "Moldyr",   "bee", "mo_sharipova@uni.kz",  "pass123", 2, "CS");
        dilara = new Student(6, "dilara", "White", "di@uni.kz","pass123", 4, "CS");

        researcherwork = new ResearcherEmployee(7, "rere", "Cutie", "ere@uni.kz", "pass123", "Research Lab", 350000.0, "Research Associate");

        profCool.addPaper(new ResearchPaper("Oracle Database In-Memory on Active Data Guard: Real-time Analytics on a Standby Database",
            List.of("Sukhada Pendse"), "IEEE Transactions", "10.1109/ICDE48307.2020.00139",
            12, LocalDate.of(2022, 3, 15), 85, ResearchTopic.DATABASES));
        profCool.addPaper(new ResearchPaper("DDOS Attack Detection in Wireless Network Based On MD",
            List.of("Noor Hassanin Hashim", "Sattar B. Sadkhan"), "ieee", " 10.1109/IT-ELA57378.2022.10107920",
            8, LocalDate.of(2023, 7, 20), 42, ResearchTopic.NETWORKS));
        profCool.addPaper(new ResearchPaper("A Code Complexity Model of Object Oriented Programming (OOP)",
            List.of("Hussam Hourani"), "ieee", " 10.1109/JEEIT.2019.8717448",
            15, LocalDate.of(2021, 12, 1), 120, ResearchTopic.OOP));

        researcherwork.addPaper(new ResearchPaper("Research on Chinese Translation of Fuzzy Semantics of English Modal Verbs Based on Quantification",
            List.of("Xia He"), "ieee", " 10.1109/ISKE47853.2019.9170391",
            10, LocalDate.of(2023, 5, 10), 30, ResearchTopic.PHILOSOFHY));

        bitCourse = new Course("CS401", "Cripta",6, 4, "CS");
        oopCourse = new Course("CS101", "Object-Oriented Programming", 5, 2, "CS");
        dbCourse  = new Course("CS201", "Databases",4, 2, "CS");

        Project = new ResearchProject("Education for Silly");

        for (User u : List.of(admin, manager, profCool, lectorAURA, togzhan, moli, dilara, researcherwork)) {
            uni.addUser(u);
        }
        for (Course c : List.of(bitCourse, oopCourse, dbCourse)) uni.addCourse(c);
        uni.addProject(Project);

        manager.addCourseForRegistration(bitCourse);
        manager.addCourseForRegistration(oopCourse);
        manager.addCourseForRegistration(dbCourse);
        manager.assignTeacher(dbCourse,  profCool);
        manager.assignTeacher(oopCourse, lectorAURA);

        try {
            moli.requestCourseRegistration(bitCourse);
            manager.approveRegistration(moli,bitCourse);
            moli.getTranscript().addRecord(bitCourse, new Mark(28, 25, 40)); // 93 = A

            togzhan.requestCourseRegistration(oopCourse);
            manager.approveRegistration(togzhan, oopCourse);
            togzhan.getTranscript().addRecord(oopCourse, new Mark(20, 22, 30)); // 72 = C

            dilara.requestCourseRegistration(bitCourse);
            manager.approveRegistration(dilara, bitCourse);
            Mark dilaraMark = new Mark(15, 12, 40); 
            dilara.getTranscript().addRecord(bitCourse, dilaraMark);
            if (!dilara.isPassed()) dilara.incrementFailCount();
        } catch (CreditLimitExceededException e) {
            System.out.println("Seed error: " + e.getMessage());
        }

        System.out.println("  [System] Loaded: " + uni.getUsers().size()
            + " users | " + uni.getCourses().size() + " courses | "
            + uni.getAllResearchers().size() + " researchers\n");
    }


    static void printMainMenu() {
        System.out.println("\n");
        System.out.println("MAIN MENU            ");
        System.out.println("1. Admin Panel                 ");
        System.out.println("2. Manager Panel               ");
        System.out.println("3. Teacher Panel               ");
        System.out.println("4. Student Panel               ");
        System.out.println("5. Research Panel              ");
        System.out.println("6. Reports                     ");
        System.out.println("0. Exit                        ");
        System.out.print("  Choice: ");
    }

    static void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── ADMIN PANEL ──");
            System.out.println("  1. View all users");
            System.out.println("  2. Search user (by name or id)");
            System.out.println("  3. Add new student");
            System.out.println("  4. Remove user by id");
            System.out.println("  5. Reset password");
            System.out.println("  6. Block user");
            System.out.println("  7. View system logs");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("  === All Users (" + uni.getUsers().size() + ") ===");
                    uni.getUsers().forEach(u -> System.out.printf("  [%d] %-20s %s%n",
                        u.getId(), u.getFullName(), u.getClass().getSimpleName()));
                }
                case 2 -> {
                    System.out.print("  Enter name or id: ");
                    String kw = sc.nextLine().trim();
                    try { System.out.println("  Found: " + admin.searchUser(kw)); }
                    catch (UserNotFoundException e) { System.out.println("  " + e.getMessage()); }
                }
                case 3 -> {
                    System.out.print("  First name: ");   String fn = sc.nextLine().trim();
                    System.out.print("  Last name: ");    String ln = sc.nextLine().trim();
                    System.out.print("  Email: ");        String em = sc.nextLine().trim();
                    System.out.print("  Password: ");     String pw = sc.nextLine().trim();
                    System.out.print("  Year (1-4): ");   int yr = readInt();
                    System.out.print("  Major: ");        String mj = sc.nextLine().trim();
                    int newId = uni.getUsers().size() + 100;
                    Student ns = new Student(newId, fn, ln, em, pw, yr, mj);
                    admin.addUser(ns);
                    uni.addUser(ns);
                }
                case 4 -> {
                    System.out.print("  User id: ");
                    String uid = sc.nextLine().trim();
                    try { admin.removeUser(uid); }
                    catch (UserNotFoundException e) { System.out.println("  " + e.getMessage()); }
                }
                case 5 -> {
                    System.out.print("  User id: ");      String uid = sc.nextLine().trim();
                    System.out.print("  New password: "); String pw  = sc.nextLine().trim();
                    try { admin.resetPassword(uid, pw); }
                    catch (UserNotFoundException | InfoNotCorrectException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                }
                case 6 -> {
                    System.out.print("  User id: ");
                    try { admin.blockUser(sc.nextLine().trim()); }
                    catch (UserNotFoundException e) { System.out.println("  " + e.getMessage()); }
                }
                case 7 -> admin.viewLogs();
                case 0 -> back = true;
                default -> System.out.println("  Invalid.");
            }
        }
    }

    static void managerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n── MANAGER PANEL ──");
            System.out.println("  1. View all courses");
            System.out.println("  2. Open course for registration");
            System.out.println("  3. Approve student registration");
            System.out.println("  4. Assign teacher to course");
            System.out.println("  5. Publish news  [Observer pattern]");
            System.out.println("  6. Students sorted by GPA");
            System.out.println("  7. Students sorted by name");
            System.out.println("  8. Teachers sorted by name");
            System.out.println("  9. View complaints");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println(" Courses");
                    uni.getCourses().forEach(c -> System.out.println("  " + c));
                }
                case 2 -> {
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c != null) manager.addCourseForRegistration(c);
                    else System.out.println("  Not found.");
                }
                case 3 -> {
                    listStudents();
                    System.out.print("  Student id: ");
                    Student s = findStudent(readInt());
                    if (s == null) { System.out.println("  Not found."); break; }
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c != null) manager.approveRegistration(s, c);
                    else System.out.println("  Not found.");
                }
                case 4 -> {
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c == null) { System.out.println("  Not found."); break; }
                    listTeachers();
                    System.out.print("  Teacher id: ");
                    Teacher t = findTeacher(readInt());
                    if (t != null) manager.assignTeacher(c, t);
                    else System.out.println("  Not found.");
                }
                case 5 -> {
                    System.out.print("  News text: ");
                    String news = sc.nextLine().trim();
                    manager.publishNews(news);
                    uni.addNews(news); 
                }
                case 6 -> manager.viewStudentsSortedByGPA(uni.getStudents());
                case 7 -> manager.viewStudentsSortedByName(uni.getStudents());
                case 8 -> manager.viewTeachersSortedByName(uni.getTeachers());
                case 9 -> manager.viewComplaints();
                case 0 -> back = true;
                default -> System.out.println("  Invalid.");
            }
        }
    }

    static void teacherMenu() {
        listTeachers();
        System.out.print("  Select teacher id: ");
        Teacher teacher = findTeacher(readInt());
        if (teacher == null) { System.out.println("  Not found."); return; }

        boolean back = false;
        while (!back) {
            System.out.println("\n── TEACHER: " + teacher.getFullName()
                + " [" + teacher.getDegree() + "] ──");
            System.out.println("  1. View my courses");
            System.out.println("  2. View students in course");
            System.out.println("  3. Put mark for student");
            System.out.println("  4. View my research papers");
            System.out.println("  5. Add research paper");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> teacher.viewCourses();
                case 2 -> {
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c != null) {
                        System.out.println("  === Students in " + c.getName() + " ===");
                        c.getEnrolledStudents().stream()
                            .sorted(Comparator.comparing(Student::getLastName))
                            .forEach(s -> System.out.println("  " + s.getFullName()));
                    } else System.out.println("  Not found.");
                }
                case 3 -> {
                    listStudents();
                    System.out.print("  Student id: ");
                    Student s = findStudent(readInt());
                    if (s == null) { System.out.println("  Not found."); break; }
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c == null) { System.out.println("  Not found."); break; }
                    System.out.print("  ATT1 (0-30): ");  int a1 = readInt();
                    System.out.print("  ATT2 (0-30): ");  int a2 = readInt();
                    System.out.print("  Final (0-40): "); int fe = readInt();
                    teacher.putMark(s, c.getName(), a1, a2, fe);
                    Mark m = new Mark(a1, a2, fe);
                    s.getTranscript().addRecord(c, m);
                    if (!m.isPassed()) {
                        try {
                            s.incrementFailCount();
                        } catch (MaxFailReachedException e) {
                            System.out.println("  !! " + e.getMessage());
                        }
                    }
                    System.out.println("  Grade: " + m.getLetterGrade() + " | Total: " + m.getTotal());
                }
                case 4 -> {
                    System.out.println("  Sort: 1-Citations  2-Date  3-Pages");
                    System.out.print("  Choice: ");
                    teacher.printPapers(pickComparator(readInt()));
                }
                case 5 -> addPaperTo(teacher);
                case 0 -> back = true;
                default -> System.out.println("  Invalid.");
            }
        }
    }

    static void studentMenu() {
        listStudents();
        System.out.print("  Select student id: ");
        Student student = findStudent(readInt());
        if (student == null) { System.out.println("  Not found."); return; }

        boolean back = false;
        while (!back) {
            System.out.printf("%n── STUDENT: %s | Year:%d | Major:%s | Credits:%d/21 | GPA:%.2f ──%n",
                student.getFullName(), student.getYear(), student.getMajor(),
                student.getCurrentCredits(), student.getGPA());
            System.out.println("  1. View available courses");
            System.out.println("  2. Request course registration");
            System.out.println("  3. View my marks");
            System.out.println("  4. View transcript + GPA");
            System.out.println("  5. Rate a teacher");
            System.out.println("  6. Assign research supervisor (4th year only)");
            System.out.println("  7. View news");
            System.out.println("  8. View teachers of a course");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("  === Open Courses ===");
                    uni.getCourses().stream()
                        .filter(Course::isOpenForRegistration)
                        .forEach(c -> System.out.printf("  [%s] %s | %d cr%n",
                            c.getCourseId(), c.getName(), c.getCredits()));
                }
                case 2 -> {
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c == null) { System.out.println("  Not found."); break; }
                    try {
                        if (student.requestCourseRegistration(c))
                            System.out.println("  Request sent! Waiting for manager approval.");
                    } catch (CreditLimitExceededException e) {
                        System.out.println("  ✗ CREDIT LIMIT: " + e.getMessage());
                    }
                }
                case 3 -> student.viewMarks();
                case 4 -> student.viewTranscript();
                case 5 -> {
                    listTeachers();
                    System.out.print("  Teacher id: ");
                    Teacher t = findTeacher(readInt());
                    if (t == null) { System.out.println("  Not found."); break; }
                    System.out.print("  Rating (1-5): ");
                    student.rateTeacher(t, readInt());
                }
                case 6 -> {
                    if (student.getYear() != 4) {
                        System.out.println("  Only 4th year students can assign a supervisor.");
                        break;
                    }
                    System.out.println("  Researchers (h-index >= 3 required):");
                    uni.getAllResearchers().forEach(r ->
                        System.out.printf("  [id=%s] %s | h-index=%d%n",
                            (r instanceof User u ? u.getId() : "?"), r, r.getHIndex()));
                    System.out.print("  Researcher user id: ");
                    int rid = readInt();
                    User ru = uni.getUsers().stream()
                        .filter(u -> u.getId() == rid && u instanceof Researcher)
                        .findFirst().orElse(null);
                    if (ru == null) { System.out.println("  Not found."); break; }
                    try { student.assignSupervisor((Researcher) ru); }
                    catch (LowHIndexException e) { System.out.println("  ✗ " + e.getMessage()); }
                }
                case 7 -> {
                    System.out.println("  === News ===");
                    List<String> newsList = uni.getNews();
                    if (newsList.isEmpty()) System.out.println("  No news.");
                    else newsList.forEach(n -> System.out.println("  • " + n));
                }
                case 8 -> {
                    listCourses();
                    System.out.print("  Course id: ");
                    Course c = findCourse(sc.nextLine().trim());
                    if (c == null) { System.out.println("  Not found."); break; }
                    System.out.println("  === Teachers of " + c.getName() + " ===");
                    if (c.getInstructors().isEmpty()) System.out.println("  No teachers assigned.");
                    else c.getInstructors().forEach(t -> System.out.println("  " + t.getFullName() + " | " + t.getDegree()
                                                                            + " | Rating: " + String.format("%.1f", t.getAverageRating())));
                }
                case 0 -> back = true;
                default -> System.out.println("  Invalid.");
            }
        }
    }

    static void researchMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nRESEARCH PANEL");
            System.out.println("  1. Print ALL papers in university (sorted)  [Facade]");
            System.out.println("  2. Top N researchers by h-index");
            System.out.println("  3. Top cited researcher of a year");
            System.out.println("  4. View research projects");
            System.out.println("  5. Add paper to researcher");
            System.out.println("  6. Join researcher to project");
            System.out.println("  7. Demo: NonResearcherException");
            System.out.println("  8. Demo: LowHIndexException");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("  Sort: 1-Citations  2-Date  3-Pages");
                    System.out.print("  Choice: ");
                    uni.printAllPapers(pickComparator(readInt()));
                }
                case 2 -> {
                    System.out.print("  How many? ");
                    uni.printTopResearchers(readInt());
                }
                case 3 -> {
                    System.out.print("  Year (e.g. 2023): ");
                    int yr = readInt();
                    uni.getTopCitedResearcherOfYear(yr).ifPresentOrElse(
                        r -> System.out.println("  Top in " + yr + ": " + r
                            + " | h-index=" + r.getHIndex()),
                        () -> System.out.println("  No papers found for year " + yr)
                    );
                }
                case 4 -> {
                    System.out.println("  === Projects ===");
                    uni.getProjects().forEach(p -> {
                        System.out.println("  " + p);
                        p.getParticipants().forEach(r -> System.out.println("    → " + r));
                    });
                }
                case 5 -> {
                    System.out.println("  All researchers:");
                    uni.getAllResearchers().forEach(r ->
                        System.out.printf("  [id=%s] %s%n",
                            (r instanceof User u ? u.getId() : "?"), r));
                    System.out.print("  Researcher user id: ");
                    int rid = readInt();
                    User ru = uni.getUsers().stream()
                        .filter(u -> u.getId() == rid && u instanceof Researcher)
                        .findFirst().orElse(null);
                    if (ru == null) { System.out.println("  Not found."); break; }
                    addPaperTo((Researcher) ru);
                }
                case 6 -> {
                    System.out.print("  Researcher user id: ");
                    int rid = readInt();
                    User ru = uni.getUsers().stream()
                        .filter(u -> u.getId() == rid && u instanceof Researcher)
                        .findFirst().orElse(null);
                    if (ru == null) { System.out.println("  Not found."); break; }
                    for (int i = 0; i < uni.getProjects().size(); i++)
                        System.out.println("  " + i + ". " + uni.getProjects().get(i).getTopic());
                    System.out.print("  Project index: ");
                    int pi = readInt();
                    if (pi < 0 || pi >= uni.getProjects().size()) {
                        System.out.println("  Invalid."); break;
                    }
                    ResearchProject proj = uni.getProjects().get(pi);
                    try {
                        if      (ru instanceof Teacher t)             t.joinProject(proj);
                        else if (ru instanceof Student s)             s.joinProject(proj);
                        else if (ru instanceof Researcher re) re.joinProject(proj);
                    } catch (NonResearcherException e) {
                        System.out.println("  ✗ " + e.getMessage());
                    }
                }
                case 7 -> {
                    System.out.println("  Demo: lector (no papers, not professor) joins Project...");
                    try {
                        lectorAURA.joinProject(Project);
                        System.out.println("  ERROR: should have thrown!");
                    } catch (NonResearcherException e) {
                        System.out.println("  ✓ NonResearcherException: " + e.getMessage());
                    }
                }
                case 8 -> {
                    System.out.println("  Demo: assign lector (h-index=0) as supervisor for me...");
                    try {
                        alice.assignSupervisor(lectorAURA);
                        System.out.println("  ERROR: should have thrown!");
                    } catch (LowHIndexException e) {
                        System.out.println("  ✓ LowHIndexException: " + e.getMessage());
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("  Invalid.");
            }
        }
    }

    static void reportMenu() {
        System.out.println("\n── REPORTS ──");
        System.out.println("  1. Full academic report");
        System.out.println("  2. Top N students by GPA");
        System.out.println("  3. Failing students");
        System.out.println("  4. Average GPA");
        System.out.print("  Choice: ");

        Report report = new Report(uni.getStudents(), uni.getCourses());
        switch (readInt()) {
            case 1 -> report.printReport();
            case 2 -> {
                System.out.print("  N: ");
                int n = readInt();
                System.out.println("  === Top " + n + " ===");
                report.getTopStudents(n).forEach(s ->
                    System.out.printf("  %s | GPA: %.2f%n", s.getFullName(), s.getGPA()));
            }
            case 3 -> {
                System.out.println("  === Failing Students ===");
                List<Student> fs = report.getFailingStudents();
                if (fs.isEmpty()) System.out.println("  None!");
                else fs.forEach(s -> System.out.printf("  %s | Fails: %d%n",
                    s.getFullName(), s.getFailCount()));
            }
            case 4 -> System.out.printf("  Average GPA: %.2f%n", report.getAverageGpa());
            default -> System.out.println("  Invalid.");
        }
    }

    static int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    static Comparator<ResearchPaper> pickComparator(int c) {
        return switch (c) {
            case 2  -> ResearchPaperComparators.BY_DATE;
            case 3  -> ResearchPaperComparators.BY_PAGES;
            default -> ResearchPaperComparators.BY_CITATIONS;
        };
    }

    static void addPaperTo(Researcher r) {
        System.out.print("  Title: ");     String title  = sc.nextLine().trim();
        System.out.print("  Journal: ");   String journal = sc.nextLine().trim();
        System.out.print("  DOI: ");       String doi    = sc.nextLine().trim();
        System.out.print("  Pages: ");     int pages     = readInt();
        System.out.print("  Citations: "); int cit       = readInt();
        System.out.print("  Year: ");      int yr        = readInt();
        System.out.println("  Topics: DATABASES  NETWORKS  OOP ");
        System.out.print("  Topic: ");     String tp = sc.nextLine().trim().toUpperCase();
        ResearchTopic topic;
        try { topic = ResearchTopic.valueOf(tp); }
        catch (IllegalArgumentException e) { topic = ResearchTopic.NETWORKS; System.out.println("  Defaulting to net"); }
        ResearchPaper p = new ResearchPaper(title, List.of("Author"), journal, doi,
            pages, LocalDate.of(yr, 1, 1), cit, topic);
        r.addPaper(p);
        System.out.println("  Success Paper added: " + p);
    }

    static void listCourses() {
        System.out.println("  Courses:");
        uni.getCourses().forEach(c -> System.out.printf("  [%s] %s | %d cr | open=%b%n",
            c.getCourseId(), c.getName(), c.getCredits(), c.isOpenForRegistration()));
    }

    static void listStudents() {
        System.out.println("  Students:");
        uni.getStudents().forEach(s -> System.out.printf("  [%d] %-15s Year:%d GPA:%.2f%n",
            s.getId(), s.getFullName(), s.getYear(), s.getGPA()));
    }

    static void listTeachers() {
        System.out.println("  Teachers:");
        uni.getTeachers().forEach(t -> System.out.printf("  [%d] %-15s %s%n",
            t.getId(), t.getFullName(), t.getDegree()));
    }

    static void listEmployees() {
        System.out.println("  Employees:");
        uni.getUsers().stream()
            .filter(u -> u instanceof Employee)
            .forEach(u -> System.out.printf("  [%d] %s%n", u.getId(), u.getFullName()));
    }

    static Course findCourse(String id) {
        return uni.getCourses().stream()
            .filter(c -> c.getCourseId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    static Student findStudent(int id) {
        return uni.getStudents().stream()
            .filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    static Teacher findTeacher(int id) {
        return uni.getTeachers().stream()
            .filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    static Employee findEmployee(int id) {
        return uni.getUsers().stream()
            .filter(u -> u instanceof Employee && u.getId() == id)
            .map(u -> (Employee) u).findFirst().orElse(null);
    }
}
