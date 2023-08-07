import java.util.ArrayList;
import java.util.Scanner;

public class Stock {
    private ArrayList<ArrayList<ArrayList<String>>> dataBarang;
    private ArrayList<String> dataKategori;

    public Stock() {
        dataBarang = new ArrayList<>();
        dataKategori = new ArrayList<>();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        int menu;
        System.out.println("\nSelamat Datang !");
        do {
            System.out.println("\n********** Menu **********");
            System.out.println("0. Keluar");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Tambah Stok");
            System.out.println("3. Kurangi Stok");
            System.out.println("4. Lihat Stok");
            System.out.print("\nPilih menu (0/1/2/3/4): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Pilihan tidak valid. Silakan pilih menu yang sesuai !");
                System.out.print("\nPilih menu (0/1/2/3/4): ");
                scanner.next();
            }
            menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    tambahBarang(scanner);
                    break;
                case 2:
                    tambahStok(scanner);
                    break;
                case 3:
                    kurangiStok(scanner);
                    break;
                case 4:
                    lihatStok();
                    break;
                case 0:
                    System.out.println("\nTerima kasih. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih menu yang sesuai.");
                    break;
            }
        } while (menu != 0);
    }

    private void tambahBarang(Scanner scanner) {
        System.out.println("\n********** Tambah Data Barang **********");
        System.out.print("Kategori: ");
        String kategori = scanner.next();
        scanner.nextLine();
        System.out.print("Nama Barang: ");
        String namaBarang = scanner.nextLine();
        int stok = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Stok: ");
            if (scanner.hasNextInt()) {
                stok = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Stok harus berupa angka !");
                scanner.next();
            }
        }

        if (stok >= 0) {
            String kodeBarang = generateKodeBarang();
            ArrayList<String> barang = new ArrayList<>();
            barang.add(kodeBarang);
            barang.add(kategori);
            barang.add(namaBarang);
            barang.add(String.valueOf(stok));

            int kategoriIndex = dataKategori.indexOf(kategori); //menyimpan indeks kategori dalam dataKategori
            if (kategoriIndex == -1) { //kategori belum ada
                dataKategori.add(kategori);
                ArrayList<ArrayList<String>> kategoriBarangList = new ArrayList<>();
                kategoriBarangList.add(barang);
                dataBarang.add(kategoriBarangList);
            } else {
                dataBarang.get(kategoriIndex).add(barang);
            }

            System.out.println("\nBarang berhasil ditambahkan.");
            lihatStok();

            //dataBarang = menyimpan data barang by kategori
            //kategoriBarangList = menyimpan data barang dengan kategori yg sama
            //barang = menyimpan detail
        } else {
            System.out.println("Stok barang tidak boleh kurang dari 0.");
        }
    }

    private void tambahStok(Scanner scanner) {
        System.out.println("\n===== Tambah Stok Barang =====");
        System.out.print("Kode Barang: ");
        String kodeBarang = scanner.next();
        int jumlahTambah = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Jumlah Stok yang Ditambahkan: ");
            if (scanner.hasNextInt()) {
                jumlahTambah = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Jumlah harus berupa angka !");
                scanner.next();
            }
        }

        boolean foundBarang = false;
        for (ArrayList<ArrayList<String>> kategoriBarangList : dataBarang) { //iterasi daftar barang berdasar kategori
            for (ArrayList<String> barang : kategoriBarangList) { // iterasi setiap barang
                if (barang.get(0).equals(kodeBarang)) {
                    int stokSekarang = Integer.parseInt(barang.get(3)); // stok indeks ke 3
                    int stokBaru = stokSekarang + jumlahTambah;
                    barang.set(3, String.valueOf(stokBaru));
                    System.out.println("Stok barang berhasil ditambahkan.");
                    lihatStok();
                    foundBarang = true;
                    break;
                }
            }
            if (foundBarang) {
                break;
            }
        }

        if (!foundBarang) {
            System.out.println("Kode barang tidak ditemukan.");
        }
    }

    private void kurangiStok(Scanner scanner) {
        System.out.println("\n********** Kurangi Stok Barang **********");
        System.out.print("Kode Barang: ");
        String kodeBarang = scanner.next();
        int jumlahKurang = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Jumlah Stok yang Dikurangi: ");
            if (scanner.hasNextInt()) {
                jumlahKurang = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Stok harus berupa angka.");
                scanner.next();
            }
        }

        boolean foundBarang = false;
        for (ArrayList<ArrayList<String>> kategoriBarangList : dataBarang) {
            for (ArrayList<String> barang : kategoriBarangList) {
                if (barang.get(0).equals(kodeBarang)) {
                    int stokSekarang = Integer.parseInt(barang.get(3));
                    int stokBaru = stokSekarang - jumlahKurang;
                    if (stokBaru >= 0) {
                        barang.set(3, String.valueOf(stokBaru));
                        System.out.println("Stok barang berhasil dikurangi.");
                        lihatStok();
                    } else {
                        System.out.println("Stok barang tidak boleh kurang dari 0.");
                    }
                    foundBarang = true;
                    break;
                }
            }
            if (foundBarang) {
                break;
            }
        }

        if (!foundBarang) {
            System.out.println("Kode barang tidak ditemukan.");
        }
    }

    private void lihatStok() {
        System.out.println("\n********** Stok Barang **********");
        for (ArrayList<ArrayList<String>> kategoriBarangList : dataBarang) { // iterasi melalui dataBarang by kategori
            for (ArrayList<String> barang : kategoriBarangList) { // iterasi detail setiap barang
                System.out.println("Kode Barang: " + barang.get(0));
                System.out.println("Kategori: " + barang.get(1));
                System.out.println("Nama Barang: " + barang.get(2));
                System.out.println("Stok: " + barang.get(3)+"\n");
            }
            System.out.println("-------------------------------");
        }
    }

    private String generateKodeBarang() {
        int totalItems = 0;
        for (ArrayList<ArrayList<String>> kategoriBarangList : dataBarang) { // iterasi daftar barang by kategori
            totalItems += kategoriBarangList.size(); // menghitung total keseluruhan barang dalam dataBarang
        }
        return "B" + (totalItems + 1);
    }
}
