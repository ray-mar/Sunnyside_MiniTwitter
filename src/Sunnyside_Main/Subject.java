package Sunnyside_Main;

public interface Subject
{
    public void attach(Observer observer);
    public void notifyObservers();
}