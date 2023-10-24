<?php

class Etudiant
{

    // Attibutes
    private $id;
    private $nom;
    private $prenom;
    private $ville;
    private $sexe;

    // Constructor

    function __construct($id, $nom, $prenom, $ville, $sexe)
    {
        $this->id = $id;
        $this->nom = $nom;
        $this->prenom = $prenom;
        $this->ville = $ville;
        $this->sexe = $sexe;
    }

    // Getters and Setters

    function getId()
    {
        return $this->id;
    }
    function getNom()
    {
        return $this->nom;
    }
    function getPrenom()
    {
        return $this->prenom;
    }
    function getVille()
    {
        return $this->ville;
    }
    function getSexe()
    {
        return $this->sexe;
    }

    // Setters

    function setId($id)
    {
        $this->id = $id;
    }
    function setNom($nom)
    {
        $this->nom = $nom;
    }
    function setPrenom($prenom)
    {
        $this->prenom = $prenom;
    }

    function setVille($ville)
    {
        $this->ville = $ville;
    }
    function setSexe($sexe)
    {
        $this->sexe = $sexe;
    }

    public function _toString(){
        return "". $this->nom ."". $this->prenom;
    }
}
