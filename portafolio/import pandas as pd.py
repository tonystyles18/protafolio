import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report

# 1. Recopilación y preparación de datos
# Suponemos que tienes un DataFrame llamado 'data' con tus datos
data = pd.read_csv('tu_archivo.csv')

# Limpiar los datos (ejemplo: eliminar valores faltantes)
data.dropna(inplace=True)

# Seleccionar las características relevantes
X = data[['caracteristica1', 'caracteristica2', 'caracteristica3']]  # Ajusta según tus características
y = data['ocurrio_incidente']  # Variable objetivo (1 si ocurrió, 0 si no)

# Dividir los datos en entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Escalar los datos (opcional, si las características tienen diferentes escalas)
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# 2. Selección del algoritmo
# En este caso, usaremos un clasificador Random Forest
model = RandomForestClassifier()

# 3. Entrenamiento del modelo
model.fit(X_train, y_train)

# 4. Evaluación del modelo
y_pred = model.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_pred))
print(classification_report(y_test, y_pred))

# 5. Depuración y ajuste
# Puedes ajustar los hiperparámetros del modelo utilizando GridSearchCV o RandomizedSearchCV
# Por ejemplo, para ajustar el número de árboles y la profundidad máxima
from sklearn.model_selection import GridSearchCV
param_grid = {'n_estimators': [100, 200, 500], 'max_depth': [5, 10, 15]}
grid_search = GridSearchCV(estimator=model, param_grid=param_grid, cv=5)
grid_search.fit(X_train, y_train)
best_params = grid_search.best_params_
print("Mejores parámetros:", best_params)

# 6. Despliegue del modelo
# Puedes usar bibliotecas como joblib para guardar el modelo y cargarlo en producción
import joblib
joblib.dump(model, 'modelo_incidente.pkl')