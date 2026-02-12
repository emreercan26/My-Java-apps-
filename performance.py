import matplotlib.pyplot as plt

liste_y=[0.14,0.20,0.93,1.99,2.77,8.64,10.06,23.77,72.19,190.95,252.39,461.72,919.44]
liste_x=[100,200,500,1000,2000,5000,10000,20000,50000,100000,200000,500000,1000000]

plt.figure(figsize=(10, 6))
plt.title("Time complexity analysis")
plt.plot(liste_x, liste_y, marker='o', linestyle='-', color='blue')

plt.xscale('log')
plt.yscale('log')

plt.ylabel("time (ms) -log. scale")
plt.xlabel("N - log. scale")
plt.grid(True, which="both", ls="--", linewidth=0.5)

plt.show() 