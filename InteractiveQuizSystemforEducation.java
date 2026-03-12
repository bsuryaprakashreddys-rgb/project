import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InteractiveQuizSystemforEducation{

    static class QuizSettings {
        String title = "DSA Quiz 1";
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        int durationMinutes = 30;
    }

    static class Question {
        int id;
        String text;
        String type; 
        List<String> options = new ArrayList<>();

        public Question(int id, String text, String type, List<String> options) {
            this.id = id;
            this.text = text;
            this.type = type;
            if (options != null) this.options = options;
        }
    }

    static class Submission {
        String studentName;
        Map<String, String> answers = new LinkedHashMap<>();
        String score = null;
        boolean isGraded = false;
        LocalDateTime submissionDate;

        public Submission(String studentName) {
            this.studentName = studentName;
            this.submissionDate = LocalDateTime.now();
        }
    }
    static QuizSettings settings = new QuizSettings();
    static List<Question> questions = new ArrayList<>();
    static List<Submission> submissions = new ArrayList<>();
    
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("🎓 Welcome to Interactive Quiz System for Education 🎓");
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. 👨‍🏫 Teacher Portal");
            System.out.println("2. 👨‍🎓 Student Portal");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    teacherLogin();
                    break;
                case "2":
                    studentLogin();
                    break;
                case "3":
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void teacherLogin() {
        System.out.println("\n--- TEACHER AUTHENTICATION ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (user.equals("KLH") && pass.equals("CSE123")) {
            teacherDashboard();
        } else {
            System.out.println("❌ Invalid Username or Password.");
        }
    }

    private static void teacherDashboard() {
        while (true) {
            System.out.println("\n--- TEACHER DASHBOARD ---");
            System.out.println("1. Setup Exam Parameters");
            System.out.println("2. Add New Question");
            System.out.println("3. View/Delete Questions");
            System.out.println("4. Grade Submissions");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": setupExam(); break;
                case "2": addQuestion(); break;
                case "3": viewQuestions(); break;
                case "4": gradeSubmissions(); break;
                case "5": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void setupExam() {
        System.out.println("\n--- EXAM PARAMETERS ---");
        System.out.print("Quiz Title (Current: " + settings.title + "): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) settings.title = title;

        System.out.print("Duration in minutes (Current: " + settings.durationMinutes + "): ");
        String dur = scanner.nextLine();
        if (!dur.isEmpty()) settings.durationMinutes = Integer.parseInt(dur);

        System.out.println("Schedule setup bypassed for console simplicity (Exam is always open).");
        System.out.println("✅ Settings Saved.");
    }

    private static void addQuestion() {
        System.out.println("\n--- ADD QUESTION ---");
        System.out.print("Question Prompt: ");
        String text = scanner.nextLine();
        
        System.out.print("Type (single/multiple/text/upload): ");
        String type = scanner.nextLine().toLowerCase();
        
        List<String> options = new ArrayList<>();
        if (type.equals("single") || type.equals("multiple")) {
            System.out.print("Enter options separated by commas: ");
            String opts = scanner.nextLine();
            options = Arrays.asList(opts.split(",\\s*"));
        }

        int id = (int) System.currentTimeMillis(); 
        questions.add(new Question(id, text, type, options));
        System.out.println("✅ Question added successfully.");
    }

    private static void viewQuestions() {
        System.out.println("\n--- CURRENT QUESTIONS (" + questions.size() + ") ---");
        if (questions.isEmpty()) {
            System.out.println("No questions created yet.");
            return;
        }

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ". " + q.text + " [" + q.type + "]");
            if (!q.options.isEmpty()) System.out.println("   Options: " + q.options);
        }

        System.out.print("Enter question number to delete (or 0 to cancel): ");
        int del = Integer.parseInt(scanner.nextLine());
        if (del > 0 && del <= questions.size()) {
            questions.remove(del - 1);
            System.out.println("🗑️ Question deleted.");
        }
    }

    private static void gradeSubmissions() {
        System.out.println("\n--- GRADING QUEUE ---");
        if (submissions.isEmpty()) {
            System.out.println("No submissions yet.");
            return;
        }

        for (int i = 0; i < submissions.size(); i++) {
            Submission sub = submissions.get(i);
            System.out.println("\nSubmission " + (i + 1) + ": " + sub.studentName);
            System.out.println("Status: " + (sub.isGraded ? "GRADED (" + sub.score + ")" : "PENDING"));
            
            for (Map.Entry<String, String> entry : sub.answers.entrySet()) {
                System.out.println("Q: " + entry.getKey());
                System.out.println("A: " + entry.getValue());
            }

            if (!sub.isGraded) {
                System.out.print("Enter grade for this student (or press Enter to skip): ");
                String grade = scanner.nextLine();
                if (!grade.isEmpty()) {
                    sub.score = grade;
                    sub.isGraded = true;
                    System.out.println("✅ Grade saved.");
                }
            }
        }
    }
    private static void studentLogin() {
        System.out.println("\n--- STUDENT IDENTIFICATION ---");
        System.out.print("Enter your Full Name: ");
        String name = scanner.nextLine();
        
        if (!name.trim().isEmpty()) {
            studentDashboard(name);
        }
    }

    private static void studentDashboard(String studentName) {
        while (true) {
            System.out.println("\n--- STUDENT PORTAL ---");
            System.out.println("Welcome, " + studentName);
            System.out.println("Quiz: " + settings.title + " | Time Limit: " + settings.durationMinutes + " mins");
            
            Submission mySub = getSubmission(studentName);
            
            if (mySub != null) {
                System.out.println("Status: SUBMITTED");
                if (mySub.isGraded) {
                    System.out.println("1. View Official Results");
                } else {
                    System.out.println("1. View Results (Grading in progress...)");
                }
            } else {
                System.out.println("Status: READY TO BEGIN");
                System.out.println("1. Start Exam");
            }
            System.out.println("2. Exit Portal");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                if (mySub != null && mySub.isGraded) {
                    System.out.println("\n--- YOUR RESULTS ---");
                    System.out.println("Score: " + mySub.score);
                } else if (mySub == null) {
                    takeExam(studentName);
                } else {
                    System.out.println("Your teacher hasn't graded this yet!");
                }
            } else if (choice.equals("2")) {
                return;
            }
        }
    }

    private static void takeExam(String studentName) {
        if (questions.isEmpty()) {
            System.out.println("❌ No questions available. Please contact your teacher.");
            return;
        }

        System.out.println("\n--- EXAM STARTED ---");
        System.out.println("You have " + settings.durationMinutes + " minutes.");
        Submission submission = new Submission(studentName);

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.text);
            
            if (q.type.equals("single") || q.type.equals("multiple")) {
                for (int j = 0; j < q.options.size(); j++) {
                    System.out.println("  " + (char)('A' + j) + ") " + q.options.get(j));
                }
                System.out.print("Your answer (type the full option text): ");
            } else if (q.type.equals("upload")) {
                System.out.print("Enter file path to upload: ");
            } else {
                System.out.print("Your answer: ");
            }
            
            String answer = scanner.nextLine();
            submission.answers.put(q.text, answer);
        }

        submissions.add(submission);
        System.out.println("\n✅ Exam submitted successfully!");
    }

    private static Submission getSubmission(String studentName) {
        for (Submission s : submissions) {
            if (s.studentName.equalsIgnoreCase(studentName)) {
                return s;
            }
        }
        return null;
    }
}