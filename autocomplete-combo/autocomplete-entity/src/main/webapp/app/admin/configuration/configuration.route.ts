import { Route } from '@angular/router';

import { AueConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'aue-configuration',
    component: AueConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
