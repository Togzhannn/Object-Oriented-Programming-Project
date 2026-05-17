package model;

import model.researcher.ResearchPaper;
import model.researcher.ResearchProject;
import model.researcher.Researcher;
import model.teacher.Teacher;

import java.util.*;


public class University {
    private static University instance;

    private final String name;
    private final List<User> users = new ArrayList<>();
    private final List<Course> courses  = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();
    private final List<String> newsFeed = new ArrayList<>(); 

    private University(String name) { this.name = name; }

    public static University getInstance(String name) {
        if (instance == null) instance = new University(name);
        return instance;
    }

    public void addUser(User user) { users.add(user); }
    public List<User> getUsers()   { return users; }

    public List<Student> getStudents() {
        return users.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u).toList();
    }

    public List<Teacher> getTeachers() {
        return users.stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u).toList();
    }

    public void addCourse(Course course) { courses.add(course); }
    public List<Course> getCourses()     { return courses; }

    public void addProject(ResearchProject project) { projects.add(project); }
    public List<ResearchProject> getProjects()      { return projects; }

    public void addNews(String news)   { newsFeed.add(news); }
    public List<String> getNews()      { return newsFeed; }

    public List<Researcher> getAllResearchers() {
        return users.stream()
                .filter(u -> u instanceof Researcher)
                .map(u -> (Researcher) u).toList();
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("  === All Research Papers ===");
        getAllResearchers().stream()
                .flatMap(r -> r.getResearchPapers().stream())
                .distinct()
                .sorted(comparator)
                .forEach(p -> System.out.println("  " + p));
    }

    public void printTopResearchers(int n) {
        System.out.println("  === Top " + n + " Researchers by H-Index ===");
        getAllResearchers().stream()
                .sorted(Comparator.comparingInt(Researcher::getHIndex).reversed())
                .limit(n)
                .forEach(r -> System.out.printf("  h=%d | %s%n", r.getHIndex(), r));
    }

    public Optional<Researcher> getTopCitedResearcherOfYear(int year) {
        return getAllResearchers().stream()
                .filter(r -> r.getResearchPapers().stream()
                        .anyMatch(p -> p.getDate().getYear() == year))
                .max(Comparator.comparingInt(r ->
                        r.getResearchPapers().stream()
                                .filter(p -> p.getDate().getYear() == year)
                                .mapToInt(ResearchPaper::getCitations)
                                .sum()));
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "University{name='" + name + "', users=" + users.size()
                + ", courses=" + courses.size() + "}";
    }


}
