package com.example.cheng.helloworld.MovieListModel;

public class MovieListData {
    private MovieListDataDates dates;
    private int page;
    private int total_pages;
    private MovieListDataResults[] results;
    private int total_results;

    public MovieListDataDates getDates() {
        return this.dates;
    }

    public void setDates(MovieListDataDates dates) {
        this.dates = dates;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public MovieListDataResults[] getResults() {
        return this.results;
    }

    public void setResults(MovieListDataResults[] results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
