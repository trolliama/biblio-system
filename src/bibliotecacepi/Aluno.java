/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi;

/**
 *
 * @author caio
 */
public class Aluno {
    private String nome;
    private String sobrenome;
    private Integer sala_id;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the sobrenome
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * @param sobrenome the sobrenome to set
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    /**
     * @return the sala_id
     */
    public Integer getSala_id() {
        return sala_id;
    }

    /**
     * @param sala_id the sala_id to set
     */
    public void setSala_id(Integer sala_id) {
        this.sala_id = sala_id;
    }
    
    
}
