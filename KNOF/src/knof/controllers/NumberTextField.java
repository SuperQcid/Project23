package knof.controllers;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

        @Override
        public void replaceText(int start, int end, String text) {
            if (validate(text)) {
                super.replaceText(start, end, text);
            }
        }

        @Override
        public void replaceSelection(String text) {
            if (validate(text)) {
                super.replaceSelection(text);
            }
        }

        /**
         * Validates input, only allowing values [0-9]
         * @param text Input
         * @return valid/invalid
         */
        private boolean validate(String text) {
            return text.matches("[0-9]*");
        }
    }