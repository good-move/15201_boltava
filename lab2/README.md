# Car Factory model

## Description
This project models cars production on a factory with the process similar to real life: 
there are component storages, managers, suppliers and dealer, assembly lines, and workers that assemble cars.

## Usage

1. Set up the environment by editing a config file `config.xml`.
2. Launch the app

## Configuration file

The `config.xml` file lets one set up the production process very flexibly.

What it must contain:

1. `<factory>` element, where types, capacity, and number of storages are listed, as well as number of workers
2. `<suppliers>` element, listing component suppliers and number of those suppliers
3. `<dealers>` element, listing car dealers with a car serial number
4. `<cars>` element, listing which cars the factory can produce. A car consists of an engine, body, and a list of accessories 
*(currently, only one accessory per car is supported)*



## Features

1. Custom car components (must be defined with a serial number)
2. Custom cars with arbitrary components
3. Integrated logging (log4j)
4. Ability to control supplement and purchase intervals in the UI
5. UI panels to live trace per-component storage load and car sales
