package application.Project;

public interface ILoader {
    Store loadFromFile(String path) throws Exception;
}