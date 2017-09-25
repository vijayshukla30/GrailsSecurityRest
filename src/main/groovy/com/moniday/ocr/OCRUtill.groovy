package com.moniday.ocr

import net.sourceforge.tess4j.ITesseract
import net.sourceforge.tess4j.Tesseract

class OCRUtill {
    static String crackImage(String filePath) {
        File file = new File(filePath)
        ITesseract iTesseract = new Tesseract()
        iTesseract.setDatapath("/home/vijay/")
        try {
            String result = iTesseract.doOCR(file)
            println result
            return result
        } catch (Exception ex) {
            ex.printStackTrace()
            return "Error Parsing"
        }
    }

    static String textFromBase64(String base64String, int id) {

        byte[] decoded = base64String.decodeBase64()
        String path = "/home/vijay/text/image${id}.png"
        new File(path).withOutputStream {
            it.write(decoded);
        }

        String data = crackImage(path)
        if (data.contains("1")) {
            return "1"
        } else if (data.contains("2")) {
            return "2"
        } else if (data.contains("3")) {
            return "3"
        } else if (data.contains("4")) {
            return "4"
        } else if (data.contains("5")) {
            return "5"
        } else if (data.contains("6")) {
            return "6"
        } else if (data.contains("7")) {
            return "7"
        } else if (data.contains("8")) {
            return "8"
        } else if (data.contains("9")) {
            return "9"
        } else if (data.contains("0")) {
            return "0"
        } else {
            return "Error->${id}"
        }
    }
}
