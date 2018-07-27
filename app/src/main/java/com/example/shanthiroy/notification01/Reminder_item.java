package com.example.shanthiroy.notification01;

public class Reminder_item {
        private String id;
        private String question;
        private String answer;


        public Reminder_item(String id, String question, String answer){
            this.id = id;
            this.question = question;
            this.answer = answer;

        }

        public String getId (){
            return this.id;
        }
        public String getQuestion (){
            return this.question;
        }
        public String getAnswer (){
            return this.answer;
        }





}
