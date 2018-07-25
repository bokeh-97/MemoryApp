package com.example.shanthiroy.notification01;

public class Reminder_item {
        private String id;
        private String question;
        private String answer;
        private String reminder_set;

        public Reminder_item(String id, String question, String answer, String reminder_set){
            this.id = id;
            this.question = question;
            this.answer = answer;
            this.reminder_set = reminder_set;
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
        public String getReminderSet (){
            return this.reminder_set;
        }




}
