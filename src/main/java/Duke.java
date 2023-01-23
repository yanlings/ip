import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Duke {
    public static void main(String[] args) throws DukeException, IOException {

        System.out.println("Hello I'm Duke");
        System.out.println("What can I do for you?");
        ArrayList<Task> array = new ArrayList<>(100);
        Scanner myObj = new Scanner(System.in);
        String reply = myObj.nextLine();
        int count = 0;

        handleLoad();
        while (!reply.startsWith("bye")) {
            if (reply.startsWith("deadline")) {
                reply = reply.replaceAll("deadline", "");
                String[] replies = reply.split("/",2);
                handleInvalidArgs checked = new handleInvalidArgs(replies);
                checked.checkForDeadline(checked.replies);
                Deadline deadline = new Deadline(replies[0],replies[1]);
                array.add(deadline);
                count += 1;
                System.out.println("Got it. I've added this task:");
                System.out.println(deadline);
                System.out.println("Now you have " + Task.actions + " tasks in the list");
                Task.tasks.add(deadline);


            }else if (reply.startsWith("todo")) {
                reply = reply.replaceAll("todo", "");
                handleInvalidArgs checked = new handleInvalidArgs(reply);
                checked.checkForToDo(checked.reply);
                ToDo todo = new ToDo(reply);
                array.add(todo);
                count += 1;
                System.out.println("Got it. I've added this task:");
                System.out.println(todo);
                System.out.println("Now you have " + Task.actions + " tasks in the list");
                Task.tasks.add(todo);

            } else if (reply.startsWith("event")) {
                reply = reply.replaceAll("event", "");
                String[] replies = reply.split("/",2);
                String[] datecheck =  replies[1].split("/");
                datecheck[0] = datecheck[0].replaceAll("from", "");
                datecheck[1] = datecheck[1].replaceAll("to","");
                replies[1] = "" + datecheck[0] + "-" + datecheck[1];
                for (int  i = 0; i < replies.length; i++) {
                    System.out.println(replies[i]);
                }
                handleInvalidArgs checked = new handleInvalidArgs(replies);
                checked.checkForEvent(checked.replies);
                Event event = new Event(replies[0],replies[1]);
                array.add(event);
                count += 1;
                System.out.println("Got it. I've added this task:");
                System.out.println(event);
                System.out.println("Now you have " + Task.actions + " tasks in the list");
                Task.tasks.add(event);

            }
            else if (reply.startsWith("unmark")) {
                int value = Integer.parseInt(reply.replaceAll("[^0-9]", "")) - 1;
                array.get(value).unmark();
            } else if (reply.startsWith("mark")) {
                int value = Integer.parseInt(reply.replaceAll("[^0-9]", "")) - 1;
                array.get(value).mark();
            }
            else if (reply.startsWith("list")) {
                System.out.println("Here are the tasks in your list:\n");
                int listcount = 1;
                for (Task element: Task.tasks) {
                    if (element != null) {
                        System.out.println("" + listcount + "." + element);
                        listcount += 1;
                    }
                }
            } else if (reply.startsWith("delete")) {
                int value = Integer.parseInt(reply.replaceAll("[^0-9]", "")) - 1;
                Task deleted = array.get(value);
                Task.actions -= 1;
                array.remove(deleted);
                System.out.println("Noted. I've removed this task: \n" + deleted);
                System.out.println("Now you have " + Task.actions + " tasks in the list");
            } else {
                handleInvalidArgs checked = new handleInvalidArgs(reply);
                checked.checkForRandomWords(checked.reply);
            }
            saveTasks();
            reply = myObj.nextLine();
        }
        System.out.println("Bye, Hope to see you again soon!");
    }

    public static void handleLoad() throws IOException {
        BufferedReader taskLoader = new BufferedReader(new FileReader(".//text-ui-test/saved-tasks.txt"));
        String words = taskLoader.readLine();
        while (words != null) {
            String[] keywords = words.split(" \\|\\| ");
            Task current = null;
            if (keywords[0].equals("todo")) {
                current = new ToDo(keywords[2]);

            } else if (keywords[0].equals("event")) {
                current = new Event(keywords[2], keywords[3]);
            } else if (keywords[0].equals("deadline")) {
                current = new Deadline(keywords[2], keywords[3]);
            } else {
                System.out.println("error");
            }

            if (keywords[1].equals("1")) {
                current.mark();
            }
            Task.tasks.add(current);
            words = taskLoader.readLine();
        }
        taskLoader.close();
    }

    public static void saveTasks() throws IOException {
        BufferedWriter taskWriter = new BufferedWriter(new FileWriter(".//text-ui-test/saved-tasks.txt"));
        String taskInString = "";
        for (int i = 0; i< (Task.tasks).size(); i++) {
            taskInString += Task.tasks.get(i).toSaveString() + "\n";
        }
        taskWriter.write(taskInString);
        taskWriter.close();
    }

}



