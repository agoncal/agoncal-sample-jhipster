import { Route } from '@angular/router';

import { JacConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'jac-configuration',
    component: JacConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
