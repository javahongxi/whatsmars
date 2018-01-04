package org.hongxi.whatsmars.base.dp.filter;

import java.nio.charset.Charset;
import java.util.zip.Adler32;

/**
 * Created by shenhongxi on 15/7/20.
 */
public class PrinterSample {
    private Printer printer;

    public PrinterSample(Printer printer){
        this.printer = printer;
    }

    public void printer(String data){
        printer.execute(data);
    }

    static interface Printer {

        public void execute(String data);
    }

    static abstract class AbstractPrinter implements Printer{
        protected Printer nextPrinter;
        protected AbstractPrinter(Printer next){
            this.nextPrinter = next;
        }
    }

    static class LengthPrinter extends AbstractPrinter{

        protected LengthPrinter(Printer next) {
            super(next);
        }

        @Override
        public void execute(String data) {
            System.out.println("length:" + data.length());
            if(nextPrinter != null){
                this.nextPrinter.execute(data);
            }
        }
    }

    static class HashcodePrinter extends AbstractPrinter{

        protected HashcodePrinter(Printer next) {
            super(next);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void execute(String data) {
            System.out.println("Hashcode:" + data.hashCode());
            if(nextPrinter != null){
                nextPrinter.execute(data);
            }
        }

    }

    static class ChecksumPrinter extends AbstractPrinter{
        private static final Charset charset = Charset.forName("utf-8");
        protected ChecksumPrinter(Printer next) {
            super(next);
        }

        @Override
        public void execute(String data) {
            Adler32 checksum = new Adler32();
            checksum.update(data.getBytes(charset));
            System.out.println("Checksum:" + checksum.getValue());
            if(nextPrinter != null){
                nextPrinter.execute(data);
            }
        }

    }


    public static void main(String[] args){
        //build chain:
        ChecksumPrinter csp = new ChecksumPrinter(null);
        HashcodePrinter hp = new HashcodePrinter(csp);
        LengthPrinter lp = new LengthPrinter(hp);
        //
        PrinterSample sample = new PrinterSample(lp);
        sample.printer("This is chain-model sample!");
    }
}
