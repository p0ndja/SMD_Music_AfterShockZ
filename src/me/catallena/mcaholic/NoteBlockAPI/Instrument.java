package me.catallena.mcaholic.NoteBlockAPI;

import org.bukkit.Sound;

public class Instrument {

    public static Sound getInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return Sound.NOTE_PIANO;
            case 1:
                return Sound.NOTE_BASS;
            case 2:
                return Sound.NOTE_BASS_DRUM;
            case 3:
                return Sound.NOTE_SNARE_DRUM;
            case 4:
                return Sound.NOTE_STICKS;
            case 5:
                return Sound.NOTE_PLING;
            case 6:
                return Sound.ORB_PICKUP;
            case 7:
            	return Sound.ITEM_PICKUP;
            case 8:
            	return Sound.CHICKEN_EGG_POP;
            default:
                return Sound.NOTE_PLING;
        }
    }

    public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return org.bukkit.Instrument.PIANO;
            case 1:
                return org.bukkit.Instrument.BASS_GUITAR;
            case 2:
                return org.bukkit.Instrument.BASS_DRUM;
            case 3:
                return org.bukkit.Instrument.SNARE_DRUM;
            case 4:
                return org.bukkit.Instrument.STICKS;
            default:
                return org.bukkit.Instrument.PIANO;
        }
    }
}
