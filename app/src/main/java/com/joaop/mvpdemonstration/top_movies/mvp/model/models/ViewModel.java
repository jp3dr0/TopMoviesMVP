package com.joaop.mvpdemonstration.top_movies.mvp.model.models;

// Model que vai adaptar os dados recebidos da API para um Model apropriado para a nossa View
public class ViewModel {
    private String name, country;

    public ViewModel(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
