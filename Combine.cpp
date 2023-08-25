#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <filesystem>

namespace fs = std::filesystem;

// Function to combine text from multiple files into a single file
void CombineTextFiles(const std::string& directoryPath, const std::string& outputFile) {
    std::ofstream combinedFile(outputFile);

    if (!combinedFile.is_open()) {
        std::cerr << "Unable to open output file: " << outputFile << std::endl;
        return;
    }

    for (const auto& entry : fs::recursive_directory_iterator(directoryPath)) {
        if (entry.is_regular_file()){// && entry.path().extension() == ".txt") {
            std::ifstream inFile(entry.path());
            if (inFile.is_open()) {
                std::string line;
                while (std::getline(inFile, line)) {
                    combinedFile << line << "\n";
                }
                inFile.close();
            } else {
                std::cerr << "Unable to open file: " << entry.path() << std::endl;
            }
        }
    }

    combinedFile.close();
}

int main() {
    std::string directoryPath = "C:/Users/Ahmed/projects/SolarApp/WatchPowerWi-Fi_1.2.0.0_Apkpure_source_from_JADX"; // Replace with your directory path
    std::string outputFile = "output.txt"; // Replace with the desired output file name

    CombineTextFiles(directoryPath, outputFile);

    std::cout << "Text files combined successfully into " << outputFile << std::endl;

    return 0;
}
