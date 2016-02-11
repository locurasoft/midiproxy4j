package com.locurasoft.midiproxy;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        Receiver receiver = null;
        Transmitter transmitter = null;
        for (MidiDevice.Info info : midiDeviceInfo) {
            System.out.println(info.getName());
            if (args.length == 0) {
                continue;
            }
            if (info.getName().startsWith(args[0]) || info.getName().startsWith(args[1])) {
                MidiDevice midiDevice = MidiSystem.getMidiDevice(info);
                if (midiDevice.getMaxReceivers() == -1) {
                    receiver = midiDevice.getReceiver();
                } else if (midiDevice.getMaxTransmitters() == -1){
                    transmitter = midiDevice.getTransmitter();
                }
            }
        }

        System.out.println(String.format("Forwarding %s to %s", args[0], args[1]));

        if (receiver == null) {
            System.out.println("Receiver is null");
            System.exit(1);
        } else if (transmitter == null) {
            System.out.println("Trasnmitter is null");
            System.exit(1);
        }

        transmitter.setReceiver(receiver);
        while (true) {
            Thread.sleep(1000);
        }
    }
}
