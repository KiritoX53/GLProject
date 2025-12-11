package gl.data;

import java.util.ArrayList;

import gl.Util;

public class Data {
    private ArrayList<Goal> goals;
    private int balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Budget> budgets;
    public Data(){
        initializeDataStructures();
    }

    private void initializeDataStructures() {
        goals = new ArrayList<>();
        transactions = new ArrayList<>();
        budgets = new ArrayList<>();
        balance = 0;
        
    }


    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Budget> getBudgets() {
        return budgets;
    }

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public void removeBudget(Budget budget) {
        this.budgets.remove(budget);
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

}