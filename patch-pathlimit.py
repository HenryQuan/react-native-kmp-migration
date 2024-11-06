import os
import subprocess

if not os.name == 'nt':
    print("This script is only for Windows.")
    exit()

# Get the current directory
current_dir = os.getcwd()

# Print out the length of the current directory
print(f"Length of the current directory: {len(current_dir)}")

# Create a virtual drive K: that maps to the current directory
try:
    subprocess.run(['subst', 'K:', current_dir], check=True)
    print(f"K: is now the root of {current_dir}")
except subprocess.CalledProcessError as e:
    reset_drive = input("Drive K: is already in use. Do you want to reset it? (y/n): ")
    if reset_drive.lower() == 'y':
        subprocess.run(['subst', 'K:', '/d'], check=True)
        print("Drive K: has been reset.")
        exit()

# Instructions to remove the new drive
print("Remove the new drive using - subst K: /d")
